package model.organization.event;

import model.organization.relation.Requires;

/**
 * The {@linkplain RequiresEventFactory} interface defines the API for constructing {@linkplain RequiresEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface RequiresEventFactory {

	/**
	 * Constructs an {@linkplain RequiresEvent}.
	 *
	 * @param requires
	 *            the {@linkplain Requires}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain RequiresEvent}.
	 */
	RequiresEvent build(Requires requires, EventCategory category);

}