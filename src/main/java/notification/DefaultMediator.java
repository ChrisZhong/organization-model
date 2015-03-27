package notification;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.StampedLock;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
class DefaultMediator implements Mediator {

	private static final Logger logger = LoggerFactory.getLogger(DefaultMediator.class);
	private final Map<Class<?>, Map<Subscriber, Method>> eventSubscribers = new ConcurrentHashMap<>();
	private final Map<Subscriber, Map<Class<?>, Method>> subscriberEvents = new ConcurrentHashMap<>();

	private final StampedLock lock = new StampedLock();

	Set<Subscriber> getSubscribers() {
		final long stamp = lock.readLock();
		try {
			return subscriberEvents.keySet();
		} finally {
			lock.unlockRead(stamp);
		}
	}

	Map<Class<?>, Method> getSubscriberEvents(final Subscriber subscriber) {
		final long stamp = lock.readLock();
		try {
			return new HashMap<>(subscriberEvents.get(subscriber));
		} finally {
			lock.unlockRead(stamp);
		}
	}

	void register(final Subscriber... subscribers) {
		for (final Subscriber subscriber : subscribers) {
			register(subscriber);
		}
	}

	void unregister(final Subscriber... subscribers) {
		for (final Subscriber subscriber : subscribers) {
			unregister(subscriber);
		}
	}

	@Override
	public <T> void post(final T event) {
		/* prevents multiple post from occurring simultaneously because it is possible for a subscriber to be notified of two or more events at the same time */
		final Set<Entry<Subscriber, Method>> set = get(event.getClass());
		synchronized (this) {
			/* notifying all subscribers for this event can be performed in parallel because a subscriber can only subscribe at most once to the same event */
			set.parallelStream().forEach(c -> invoke(c.getValue(), c.getKey(), event));
		}
	}

	@Override
	public void register(final Subscriber subscriber) {
		Arrays.stream(subscriber.getClass().getDeclaredMethods()).parallel().filter(p -> p.isAnnotationPresent(Subscribe.class) && p.getParameterCount() == 1)
				.forEach(c -> add(c, subscriber));
	}

	@Override
	public void unregister(final Subscriber subscriber) {
		final long stamp = lock.writeLock();
		try {
			if (subscriberEvents.containsKey(subscriber)) {
				final Map<Class<?>, Method> map = subscriberEvents.remove(subscriber);
				map.keySet().parallelStream().forEach(c -> {
					final Map<Subscriber, Method> m = eventSubscribers.get(c);
					m.remove(subscriber);
					if (m.isEmpty()) {
						eventSubscribers.remove(c);
					}
				});
			}
		} finally {
			lock.unlockWrite(stamp);
		}
	}

	private Set<Entry<Subscriber, Method>> get(final Class<?> type) {
		final long stamp = lock.readLock();
		try {
			if (eventSubscribers.containsKey(type)) {
				return eventSubscribers.get(type).entrySet();
			}
		} finally {
			lock.unlockRead(stamp);
		}
		return new HashSet<>();
	}

	private <T> void invoke(final Method method, final Subscriber subscriber, final T event) {
		try {
			method.invoke(subscriber, event);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			logger.warn("Unable to invoke {}.{}({})", subscriber.getClass().getName(), method.getName(), event);
			final long stamp = lock.writeLock();
			try {
				final Map<Subscriber, Method> map = eventSubscribers.get(event.getClass());
				map.remove(subscriber);
				if (map.isEmpty()) {
					eventSubscribers.remove(event.getClass());
				}
			} finally {
				lock.unlockWrite(stamp);
			}
		}
	}

	private void add(final Method method, final Subscriber subscriber) {
		final Class<?> type = method.getParameterTypes()[0];
		final long stamp = lock.writeLock();
		try {
			final Map<Subscriber, Method> map = eventSubscribers.computeIfAbsent(type, f -> new ConcurrentHashMap<>());
			if (map.containsKey(subscriber)) {
				logger.warn("Subscriber ({}) is already registered to ({})", subscriber, type);
			} else {
				logger.info("Registering ({}) for ({}) with ({})", subscriber, type, method);
				map.put(subscriber, method);
				subscriberEvents.computeIfAbsent(subscriber, f -> new ConcurrentHashMap<>()).put(type, method);
			}
		} finally {
			lock.unlockWrite(stamp);
		}
	}

}
