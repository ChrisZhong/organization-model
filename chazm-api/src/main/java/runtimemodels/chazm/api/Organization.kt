package runtimemodels.chazm.api

import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.manager.*
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.api.relation.manager.*
import java.util.function.Predicate

/**
 * The [Organization] interface defines an organization, which consists of entities and relations between the entities.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
interface Organization : AgentManager, AttributeManager, CapabilityManager, CharacteristicManager, InstanceGoalManager, PmfManager, PolicyManager, RoleManager, SpecificationGoalManager, AchievesManager, AssignmentManager, ContainsManager, HasManager, ModeratesManager, NeedsManager, PossessesManager, RequiresManager, UsesManager {

    /**
     * Checks if this [Organization] is valid. Validity rules differ from one organization to another, so there is no general algorithm to determine
     * the validity of an [Organization].
     *
     * @param p the [Predicate] that tests the validity of this [Organization].
     * @return `true` if the [Organization] is valid, `false` otherwise.
     */
    fun isValid(p: Predicate<Organization>): Boolean {
        return p.test(this)
    }

    /**
     * Returns a `double`-valued score of this [Organization]'s effectiveness.
     *
     * @param assignments a set of [Assignment]s.
     * @return a `double`-valued score.
     */
    fun effectiveness(assignments: Collection<Assignment>): Double

    /**
     * Returns the [Goodness] function associated with a [Role] from this [Organization].
     *
     * @param id the [UniqueId] that represents a [Role].
     * @return a [Goodness] function.
     */
    fun getGoodness(id: UniqueId<Role>): Goodness?

    /**
     * Sets a new [Goodness] function for a [Role] in this [Organization].
     *
     * @param id       the [UniqueId] that represents a [Role].
     * @param goodness the [Goodness] function.
     */
    fun setGoodness(id: UniqueId<Role>, goodness: Goodness)

}