package org.models.organization.manager;

import java.util.Collection;
import java.util.Set;

import org.models.organization.entity.InstanceGoal;
import org.models.organization.identifier.UniqueId;

/**
 * The {@linkplain InstanceGoalManager} interface defines the necessary APIs for managing {@linkplain InstanceGoal}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface InstanceGoalManager {
	/**
	 * Adds an {@linkplain InstanceGoal} to this {@linkplain InstanceGoalManager}.
	 *
	 * @param goal
	 *            the {@linkplain InstanceGoal} to add.
	 */
	void addInstanceGoal(InstanceGoal<?> goal);

	/**
	 * Adds a set of {@linkplain InstanceGoal}s to this {@linkplain InstanceGoalManager}.
	 *
	 * @param goals
	 *            the set of {@linkplain InstanceGoal} to add.
	 */
	void addInstanceGoals(Collection<InstanceGoal<?>> goals);

	/**
	 * Adds a set of {@linkplain InstanceGoal}s to this {@linkplain InstanceGoalManager}.
	 *
	 * @param goals
	 *            the set of {@linkplain InstanceGoal} to add.
	 */
	void addInstanceGoals(InstanceGoal<?>... goals);

	/**
	 * Returns an {@linkplain InstanceGoal} from this {@linkplain InstanceGoalManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain InstanceGoal} to retrieve.
	 * @return the {@linkplain InstanceGoal} if it exists, <code>null</code> otherwise.
	 */
	InstanceGoal<?> getInstanceGoal(UniqueId id);

	/**
	 * Returns a set of {@linkplain InstanceGoal} from this {@linkplain InstanceGoalManager}.
	 *
	 * @return the set of {@linkplain InstanceGoal}.
	 */
	Set<InstanceGoal<?>> getInstanceGoals();

	/**
	 * Removes an {@linkplain InstanceGoal} from this {@linkplain InstanceGoalManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain InstanceGoal} to remove.
	 */
	void removeInstanceGoal(UniqueId id);

	/**
	 * Removes a set of {@linkplain InstanceGoal}s from this {@linkplain InstanceGoalManager}.
	 *
	 * @param ids
	 *            the set of {@linkplain UniqueId} that represents the {@linkplain InstanceGoal}s to remove.
	 */
	void removeInstanceGoals(Collection<UniqueId> ids);

	/**
	 * Removes a set of {@linkplain InstanceGoal}s from this {@linkplain InstanceGoalManager}.
	 *
	 * @param ids
	 *            the set of {@linkplain UniqueId} that represents the {@linkplain InstanceGoal}s to remove.
	 */
	void removeInstanceGoals(UniqueId... ids);

	/**
	 * Removes all {@linkplain InstanceGoal}s from this {@linkplain InstanceGoalManager}.
	 */
	void removeAllInstanceGoals();
}