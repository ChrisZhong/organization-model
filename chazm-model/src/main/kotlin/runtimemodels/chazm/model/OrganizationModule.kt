package runtimemodels.chazm.model

import com.google.inject.AbstractModule
import com.google.inject.Provides
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.organization.Agents
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.event.EventModule
import runtimemodels.chazm.model.function.FunctionModule
import runtimemodels.chazm.model.notification.NotificationModule
import runtimemodels.chazm.model.organizations.DefaultAgents
import runtimemodels.chazm.model.organizations.DefaultOrganization
import runtimemodels.chazm.model.relation.RelationModule

/**
 * The [OrganizationModule] class provides a Guice binding module for entities.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class OrganizationModule : AbstractModule() {

    @Provides
    fun agents(): MutableMap<AgentId, Agent> = mutableMapOf()

    override fun configure() {
        bind(Organization::class.java).to(DefaultOrganization::class.java)
        bind(Agents::class.java).to(DefaultAgents::class.java)
        install(RelationModule())
        install(FunctionModule())
        install(EventModule())
        install(NotificationModule())
    }

}
