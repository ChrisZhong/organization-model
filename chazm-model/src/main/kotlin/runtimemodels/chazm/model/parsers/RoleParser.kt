package runtimemodels.chazm.model.parsers

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.factory.EntityFactory
import runtimemodels.chazm.model.factory.RelationFactory
import runtimemodels.chazm.model.id.DefaultRoleId
import runtimemodels.chazm.model.message.E
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement

@Singleton
internal class RoleParser @Inject constructor(
    private val entityFactory: EntityFactory,
    private val relationFactory: RelationFactory
) {
    fun canParse(qName: QName): Boolean = ROLE_ELEMENT == qName.localPart

    fun parse(element: StartElement, roles: MutableMap<String, RoleId>, organization: Organization, reader: XMLEventReader, name: QName, attributes: MutableMap<String, AttributeId>, capabilities: MutableMap<String, CapabilityId>, characteristics: MutableMap<String, CharacteristicId>, specificationGoals: MutableMap<String, SpecificationGoalId>, list1: MutableList<() -> Unit>) {
        val id = DefaultRoleId(element attribute NAME_ATTRIBUTE)
        build(id, roles, element, { entityFactory.build(it) }, { organization.add(it) })
        parseRole(organization, reader, name, id, attributes, capabilities, characteristics, specificationGoals, list1)
    }

    @Throws(XMLStreamException::class)
    private fun parseRole(organization: Organization, reader: XMLEventReader, tagName: QName, id: RoleId,
                          attributes: Map<String, AttributeId>, capabilities: Map<String, CapabilityId>,
                          characteristics: Map<String, CharacteristicId>, goals: Map<String, SpecificationGoalId>, list: MutableList<() -> Unit>) {
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                when (name.localPart) {
                    ACHIEVES_ELEMENT -> {
                        val ids = collectIds(reader, name)
                        list.add {
                            addRelation(
                                id,
                                ids,
                                goals,
                                { roleId, goalId ->
                                    organization.achievesRelations.add(relationFactory.build(
                                        organization.roles[roleId]!!,
                                        organization.specificationGoals[goalId]!!
                                    ))
                                },
                                SpecificationGoal::class.java
                            )
                        }
                    }
                    CONTAINS_ELEMENT -> {
                        val ids = collectIds(reader, name)
                        try {
                            val value = java.lang.Double.valueOf(element attribute VALUE_ATTRIBUTE)
                            list.add {
                                addRelation(
                                    id,
                                    ids,
                                    characteristics,
                                    { roleId, characteristicId ->
                                        organization.containsRelations.add(relationFactory.build(
                                            organization.roles[roleId]!!,
                                            organization.characteristics[characteristicId]!!,
                                            value
                                        ))
                                    },
                                    Characteristic::class.java
                                )
                            }
                        } catch (e: NumberFormatException) {
                            throw XMLStreamException(e)
                        }

                    }
                    NEEDS_ELEMENT -> {
                        val ids = collectIds(reader, name)
                        list.add {
                            addRelation(
                                id,
                                ids,
                                attributes,
                                { roleId, attributeId ->
                                    organization.needsRelations.add(relationFactory.build(
                                        organization.roles[roleId]!!,
                                        organization.attributes[attributeId]!!
                                    ))
                                },
                                Attribute::class.java
                            )
                        }
                    }
                    REQUIRES_ELEMENT -> {
                        val ids = collectIds(reader, name)
                        list.add {
                            addRelation(
                                id,
                                ids,
                                capabilities,
                                { roleId, capabilityId ->
                                    organization.requiresRelations.add(relationFactory.build(
                                        organization.roles[roleId]!!,
                                        organization.capabilities[capabilityId]!!
                                    ))
                                },
                                Capability::class.java
                            )
                        }
                    }
                }
            } else if (event.isEndElement) {
                val element = event.asEndElement()
                if (element.name == tagName) {
                    return
                }
            }
        }
        throw XMLStreamException(E.MISSING_END_TAG[tagName]) // should not happen as XMLEventReader will do it for us
    }

    companion object {
        private const val ROLE_ELEMENT = "Role" //$NON-NLS-1$
        private const val ACHIEVES_ELEMENT = "achieves" //$NON-NLS-1$
        private const val CONTAINS_ELEMENT = "contains" //$NON-NLS-1$
        private const val NEEDS_ELEMENT = "needs" //$NON-NLS-1$
        private const val REQUIRES_ELEMENT = "requires" //$NON-NLS-1$
        private const val NAME_ATTRIBUTE = "name" //$NON-NLS-1$
        private const val VALUE_ATTRIBUTE = "value" //$NON-NLS-1$
    }
}
