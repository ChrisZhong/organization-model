package runtimemodels.chazm.model.event

import runtimemodels.chazm.api.entity.Capability

/**
 * The [CapabilityEventFactory] interface defines the API for constructing [CapabilityEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface CapabilityEventFactory {

    /**
     * Constructs an [CapabilityEvent].
     *
     * @param category   the [EventType].
     * @param capability the [Capability].
     * @return a [CapabilityEvent].
     */
    fun build(category: EventType, capability: Capability): CapabilityEvent

}
