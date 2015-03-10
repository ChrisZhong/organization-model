/*
 * CapabilityImpl.java
 *
 * Created on Oct 5, 2004
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.models.organization.identifier.UniqueId;
import org.models.organization.relation.Possesses;
import org.models.organization.relation.PossessesRelation;
import org.models.organization.relation.Requires;
import org.models.organization.relation.RequiresRelation;

/**
 * The {@link CapabilityEntity} class is an implementation of the {@link Capability}.
 *
 * @author Scott Harmon, Christopher Zhong
 * @see Agent
 * @see Role
 * @since 1.0
 */
public class CapabilityEntity implements Capability {
	/**
	 * The {@linkplain UniqueId} that represents this {@linkplain Capability}.
	 */
	private final UniqueId id;

	/**
	 * The set of <code>Agent</code> that possesses this <code>Capability</code> .
	 */
	private final Map<UniqueId, PossessesRelation> possessedBy = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Role</code> that requires this <code>Capability</code>.
	 */
	private final Map<UniqueId, RequiresRelation> requiredBy = new ConcurrentHashMap<>();

	/**
	 * Constructs a new instance of {@linkplain Capability}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@linkplain Capability}.
	 */
	public CapabilityEntity(final UniqueId id) {
		if (id == null) {
			throw new IllegalArgumentException("Parameter (id) cannot be null");
		}
		this.id = id;
	}

	@Override
	public final UniqueId getId() {
		return id;
	}

	/**
	 * Adds the given <code>PossessesRelation</code> to the set of <code>PossessesRelation</code> that is possessed by this <code>Capability</code>.
	 * <p>
	 * The <code>PossessesRelation</code> will be indexed by the <code>String</code> identifying the <code>Agent</code> that is associated with the given
	 * <code>PossessesRelation</code>.
	 *
	 * @param possessesRelation
	 *            the <code>PossessesRelation</code> to be added.
	 */
	final void addPossessedBy(final PossessesRelation possessesRelation) {
		if (possessesRelation == null) {
			throw new IllegalArgumentException("Parameter (possessesRelation) cannot be null");
		}
		possessedBy.put(possessesRelation.getAgent().getId(), possessesRelation);
	}

	@Override
	public final Set<Agent> getPossessedBySet() {
		final Set<Agent> result = new HashSet<>();
		for (final Possesses possessesRelaton : possessedBy.values()) {
			result.add(possessesRelaton.getAgent());
		}
		return result;
	}

	@Override
	public final double getPossessedByScore(final UniqueId agentIdentifier) {
		if (agentIdentifier == null) {
			throw new IllegalArgumentException("Parameter (agentIdentifier) cannot be null");
		}
		final Possesses possessesRelation = possessedBy.get(agentIdentifier);
		return possessesRelation == null ? Possesses.MIN_SCORE : possessesRelation.getScore();
	}

	/**
	 * Removes the given <code>Agent</code> from the set of <code>Agent</code> that possesses this <code>Capability</code>.
	 *
	 * @param agent
	 *            the <code>Agent</code> to be removed.
	 */
	final void removePossessedBy(final Agent agent) {
		if (agent == null) {
			throw new IllegalArgumentException("Parameter (agent) cannot be null");
		}
		possessedBy.remove(agent.getId());
	}

	/**
	 * Adds the given <code>RequiresRelation</code> to the set of <code>RequiresRelation</code> that this <code>Capability</code> requires.
	 *
	 * @param requiresRelation
	 *            the <code>RequiresRelation</code> to be added.
	 */
	final void addRequiredBy(final RequiresRelation requiresRelation) {
		if (requiresRelation == null) {
			throw new IllegalArgumentException("Parameter (requiresRelation) cannot be null");
		}
		requiredBy.put(requiresRelation.getRole().getId(), requiresRelation);
	}

	@Override
	public final Set<Role> getRequiredBySet() {
		final Set<Role> result = new HashSet<>();
		for (final Requires requiresRelation : requiredBy.values()) {
			result.add(requiresRelation.getRole());
		}
		return result;
	}

	/**
	 * Remove the given <code>Role</code> from the set of <code>Role</code> that this <code>Capability</code> requires.
	 *
	 * @param role
	 *            the <code>Role</code> to be removed.
	 */
	final void removeRequiredBy(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException("Parameter (role) cannot be null");
		}
		requiredBy.remove(role.getId());
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Capability) {
			final Capability capability = (Capability) object;
			return getId().equals(capability.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		return getId().toString();
	}
}