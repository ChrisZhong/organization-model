package runtimemodels.chazm.model.event

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Possesses
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [PossessesEvent] class indicates that there is an update about a [Possesses] relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class PossessesEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted possesses: Possesses
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents an [Agent].
     *
     * @return a [UniqueId].
     */
    val agentId: UniqueId<Agent> = possesses.agent.id
    /**
     * Returns a [UniqueId] that represents a [Capability].
     *
     * @return a [UniqueId].
     */
    val capabilityId: UniqueId<Capability> = possesses.capability.id
    /**
     * Returns a `double` score.
     *
     * @return a `double` score.
     */
    val score: Double = possesses.score

    override fun equals(other: Any?): Boolean {
        if (other is PossessesEvent) {
            return super.equals(other) && agentId == other.agentId && capabilityId == other.capabilityId
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, agentId, capabilityId)

    override fun toString(): String = M.EVENT_WITH_2_IDS_AND_VALUE[super.toString(), agentId, capabilityId, score]

    companion object {
        private const val serialVersionUID = 1318353518805222229L
    }

}
