package runtimemodels.chazm.api.organization

import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.SpecificationGoalId
import runtimemodels.chazm.api.relation.Achieves

/**
 * The [AchievesManager] interface defines the APIs for managing a set of [Achieves] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface AchievesManager : Map<RoleId, Map<SpecificationGoalId, Achieves>> {
    /**
     * Adds an [Achieves] relation to this [AchievesManager].
     *
     * @param achieves the [Achieves] relation to add.
     */
    fun add(achieves: Achieves)

    /**
     * Returns a [Map] of the [SpecificationGoal]s that are achieved by a [Role].
     *
     * @param key the [RoleId] that represents the [Role].
     */
    override operator fun get(key: RoleId): Map<SpecificationGoalId, Achieves>

    /**
     * Returns a [Map] of the [Role]s that achieves a [SpecificationGoal].
     *
     * @param id the [SpecificationGoalId] that represents the [SpecificationGoal].
     */
    operator fun get(id: SpecificationGoalId): Map<RoleId, Achieves>

    /**
     * Removes an [Achieves] relation between a [Role] and a [SpecificationGoal] from this [AchievesManager].
     *
     * @param roleId the [RoleId] that represents the [Role].
     * @param goalId the [SpecificationGoalId] that represents the [SpecificationGoal].
     * @return the [Achieves] relation that was removed.
     */
    fun remove(roleId: RoleId, goalId: SpecificationGoalId): Achieves

    /**
     * Removes all [Achieves] relations associated to a [Role] from this [AchievesManager].
     *
     * @param id the [RoleId] that represents the [Role].
     */
    fun remove(id: RoleId)

    /**
     * Removes all [Achieves] relations associated to a [SpecificationGoal] from this [AchievesManager].
     *
     * @param id the [SpecificationGoalId] that represents the [SpecificationGoal].
     */
    fun remove(id: SpecificationGoalId)
}
