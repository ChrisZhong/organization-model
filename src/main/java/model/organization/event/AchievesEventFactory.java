package model.organization.event;

import model.organization.relation.Achieves;

/**
 * The {@linkplain AchievesEventFactory} interface defines the API for constructing {@linkplain AchievesEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface AchievesEventFactory {

	/**
	 * Constructs an {@linkplain AchievesEvent}.
	 *
	 * @param achieves
	 *            the {@linkplain Achieves}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return an {@linkplain AchievesEvent}.
	 */
	AchievesEvent build(Achieves achieves, EventCategory category);

}