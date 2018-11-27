package runtimemodels.chazm.api.relation.manager;

import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.id.AgentId;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Has;

import java.util.Set;

/**
 * The {@linkplain HasManager} interface defines the APIs for managing {@linkplain Has}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface HasManager {
    /**
     * Creates a has relation between an {@linkplain Agent} and an {@linkplain Attribute} in this {@linkplain HasManager}.
     *
     * @param agentId     the {@linkplain UniqueId} that represents the {@linkplain Agent}.
     * @param attributeId the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
     * @param value       the <code>double</code> value.
     */
    void addHas(AgentId agentId, UniqueId<Attribute> attributeId, double value);

    /**
     * Returns a set of {@linkplain Attribute}s that is had by an {@linkplain Agent} in this {@linkplain HasManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
     * @return a set of {@linkplain Attribute}s.
     */
    Set<Attribute> getHas(AgentId id);

    /**
     * Returns a set of {@linkplain Agent}s that has an {@linkplain Attribute} in this {@linkplain HasManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Agent}.
     * @return a set of {@linkplain Agent}s.
     */
    Set<Agent> getHadBy(UniqueId<Attribute> id);

    /**
     * Returns the <code>double</code> value of the has relation between an {@linkplain Agent} and an {@linkplain Attribute} in this {@linkplain HasManager}.
     *
     * @param agentId     the {@linkplain UniqueId} that represents the {@linkplain Agent}.
     * @param attributeId the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
     * @return the <code>double</code> value if the has relation exists, <code>null</code> otherwise.
     */
    Double getHasValue(AgentId agentId, UniqueId<Attribute> attributeId);

    /**
     * Sets a new <code>double</code> value for the needs relation between an {@linkplain Agent} and an {@linkplain Attribute} in this {@linkplain HasManager}.
     *
     * @param agentId     the {@linkplain UniqueId} that represents the {@linkplain Agent}.
     * @param attributeId the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
     * @param value       the new <code>double</code> value.
     */
    void setHasValue(AgentId agentId, UniqueId<Attribute> attributeId, double value);

    /**
     * Removes a has relation between an {@linkplain Agent} and an {@linkplain Attribute} from this {@linkplain HasManager}.
     *
     * @param agentId     the {@linkplain UniqueId} that represents the {@linkplain Agent}.
     * @param attributeId the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
     */
    void removeHas(AgentId agentId, UniqueId<Attribute> attributeId);

    /**
     * Removes all has relations from this {@linkplain HasManager}.
     */
    void removeAllHas();
}
