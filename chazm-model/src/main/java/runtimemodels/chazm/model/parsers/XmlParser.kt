package runtimemodels.chazm.model.parsers

import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.entity.EntityFactory
import runtimemodels.chazm.model.id.IdFactory
import runtimemodels.chazm.model.message.E
import runtimemodels.chazm.model.message.L
import runtimemodels.chazm.model.relation.AssignmentFactory
import java.io.InputStream
import java.util.*
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Function
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement

/**
 * The [XmlParser] class is able to parse XML files and populate an [Organization].
 *
 * @author Christopher Zhong
 * @since 4.0
 */
@Singleton
internal open class XmlParser @Inject constructor(
    private val idFactory: IdFactory,
    private val entityFactory: EntityFactory,
    private val assignmentFactory: AssignmentFactory
) {

    @FunctionalInterface
    private interface RunLater {
        @Throws(XMLStreamException::class)
        fun run()
    }

    /**
     * Parses an XML file and populates an [Organization].
     *
     * @param organization the [Organization].
     * @param inputStream  the [InputStream].
     * @throws XMLStreamException
     */
    @Throws(XMLStreamException::class)
    fun parse(organization: Organization, inputStream: InputStream) {
        val factory = XMLInputFactory.newInstance()
        val reader = factory.createXMLEventReader(inputStream)
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                log.debug(L.START_TAG.get(), element)
                val name = element.name
                if (ROLE_DIAGRAM_ELEMENT == name.localPart) {
                    parseDiagram(organization, reader, name)
                }
            }
        }
    }

    @Throws(XMLStreamException::class)
    private fun parseDiagram(organization: Organization, reader: XMLEventReader, tagName: QName) {
        val agents = HashMap<String, UniqueId<Agent>>()
        val attributes = HashMap<String, UniqueId<Attribute>>()
        val capabilities = HashMap<String, UniqueId<Capability>>()
        val characteristics = HashMap<String, UniqueId<Characteristic>>()
        val instanceGoals = HashMap<String, UniqueId<InstanceGoal>>()
        val pmfs = HashMap<String, UniqueId<Pmf>>()
        val policies = HashMap<String, UniqueId<Policy>>()
        val roles = HashMap<String, UniqueId<Role>>()
        val specificationGoals = HashMap<String, UniqueId<SpecificationGoal>>()
        val list1 = ArrayList<RunLater>()
        val list2 = ArrayList<RunLater>()
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                if (AGENT_ELEMENT == name.localPart) {
                    // TODO parse contact info
                    val id = idFactory.build(Agent::class.java, getAttributeValue(element, NAME_ATTRIBUTE))
                    val contactInfo = object : Agent.ContactInfo {

                    }
                    build(id, agents, element, Function { entityFactory.buildAgent(it, contactInfo) }, Consumer { organization.addAgent(it) })
                    parseAgent(organization, reader, name, id, attributes, capabilities, list1)
                } else if (ASSIGNMENT_ELEMENT == name.localPart) {
                    parseAssignment(organization, element, agents, instanceGoals, roles, list2)
                } else if (ATTRIBUTE_ELEMENT == name.localPart) {
                    val id = idFactory.build(Attribute::class.java, getAttributeValue(element, NAME_ATTRIBUTE))
                    try {
                        val type = Attribute.Type.valueOf(getAttributeValue(element, TYPE_ATTRIBUTE))
                        build(id, attributes, element, Function { entityFactory.buildAttribute(it, type) }, Consumer { organization.addAttribute(it) })
                    } catch (e: IllegalArgumentException) {
                        throw XMLStreamException(e)
                    }

                } else if (CAPABILITY_ELEMENT == name.localPart) {
                    val id = idFactory.build(Capability::class.java, getAttributeValue(element, NAME_ATTRIBUTE))
                    build(id, capabilities, element, Function { entityFactory.buildCapability(it) }, Consumer { organization.addCapability(it) })
                } else if (CHARACTERISTIC_ELEMENT == name.localPart) {
                    val id = idFactory.build(Characteristic::class.java, getAttributeValue(element, NAME_ATTRIBUTE))
                    build(id, characteristics, element, Function { entityFactory.buildCharacteristic(it) }, Consumer { organization.addCharacteristic(it) })
                } else if (INSTANCEGOAL_ELEMENT == name.localPart) {
                    parseInstanceGoal(organization, element, specificationGoals, instanceGoals, list1)
                } else if (PMF_ELEMENT == name.localPart) {
                    val id = idFactory.build(Pmf::class.java, getAttributeValue(element, NAME_ATTRIBUTE))
                    build(id, pmfs, element, Function { entityFactory.buildPmf(it) }, Consumer { organization.addPmf(it) })
                    parsePmf(organization, reader, name, id, attributes, list1)
                } else if (POLICY_ELEMENT == name.localPart) {
                    val id = idFactory.build(Policy::class.java, getAttributeValue(element, NAME_ATTRIBUTE))
                    build(id, policies, element, Function { entityFactory.buildPolicy(it) }, Consumer { organization.addPolicy(it) })
                } else if (ROLE_ELEMENT == name.localPart) {
                    val id = idFactory.build(Role::class.java, getAttributeValue(element, NAME_ATTRIBUTE))
                    build(id, roles, element, Function { entityFactory.buildRole(it) }, Consumer { organization.addRole(it) })
                    parseRole(organization, reader, name, id, attributes, capabilities, characteristics, specificationGoals, list1)
                } else if (GOAL_ELEMENT == name.localPart) {
                    val id = idFactory.build(SpecificationGoal::class.java, getAttributeValue(element, NAME_ATTRIBUTE))
                    build(id, specificationGoals, element, Function { entityFactory.buildSpecificationGoal(it) }, Consumer { organization.addSpecificationGoal(it) })
                }
            } else if (event.isEndElement) {
                val element = event.asEndElement()
                if (element.name == tagName) {
                    for (r in list1) {
                        r.run()
                    }
                    for (r in list2) {
                        r.run()
                    }
                    return
                }
            }
        }
        throw XMLStreamException(E.MISSING_END_TAG[tagName]) // should not happen as XMLEventReader will do it for us
    }

    @Throws(XMLStreamException::class)
    private fun <T : UniqueId<U>, U> build(
        id: T,
        map: MutableMap<String, T>,
        element: StartElement,
        f: Function<T, U>,
        c: Consumer<U>
    ) {
        map[getAttributeValue(element, ID_ATTRIBUTE)] = id
        val t = f.apply(id)
        try {
            c.accept(t)
        } catch (e: IllegalArgumentException) {
            throw XMLStreamException(e)
        }

    }

    @Throws(XMLStreamException::class)
    private fun getAttributeValue(element: StartElement, localPart: String): String {
        val attribute = element.getAttributeByName(QName(localPart))
            ?: throw XMLStreamException(E.MISSING_ATTRIBUTE_IN_TAG[element.name.localPart, localPart])
        return attribute.value
    }

    @Throws(XMLStreamException::class)
    private fun parseAgent(organization: Organization, reader: XMLEventReader, tagName: QName, id: UniqueId<Agent>,
                           attributes: Map<String, UniqueId<Attribute>>, capabilities: Map<String, UniqueId<Capability>>, list: MutableList<RunLater>) {
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                if (HAS_ELEMENT == name.localPart) {
                    val ids = collectChild(reader, name)
                    try {
                        val value = java.lang.Double.valueOf(getAttributeValue(element, VALUE_ATTRIBUTE))
                        list.add(object : RunLater {
                            override fun run() {
                                return addRelation(id, ids, attributes, BiConsumer { c, d -> organization.addHas(c, d, value) }, Attribute::class.java)
                            }
                        })
                    } catch (e: NumberFormatException) {
                        throw XMLStreamException(e)
                    }

                } else if (POSSESSES_ELEMENT == name.localPart) {
                    val ids = collectChild(reader, name)
                    try {
                        val value = java.lang.Double.valueOf(getAttributeValue(element, SCORE_ATTRIBUTE))
                        list.add(object : RunLater {
                            override fun run() {
                                return addRelation(id, ids, capabilities, BiConsumer { c, d -> organization.addPossesses(c, d, value) }, Capability::class.java)
                            }
                        })
                    } catch (e: NumberFormatException) {
                        throw XMLStreamException(e)
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

    private fun parseAssignment(organization: Organization, element: StartElement, agents: Map<String, UniqueId<Agent>>,
                                instanceGoals: Map<String, UniqueId<InstanceGoal>>, roles: Map<String, UniqueId<Role>>, list: MutableList<RunLater>) {
        /* construction of an assignment depends on the existence of the instance goal */
        list.add(object : RunLater {
            override fun run() {
                val aId = getAttributeValue(element, AGENT_ATTRIBUTE)
                val agentId = agents[aId]
                    ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[Agent::class.java.simpleName, aId])
                val agent = organization.getAgent(agentId)
                val rId = getAttributeValue(element, ROLE_ATTRIBUTE)
                val roleId = roles[rId]
                    ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[Role::class.java.simpleName, rId])
                val role = organization.getRole(roleId)
                val gId = getAttributeValue(element, GOAL_ATTRIBUTE)
                val goalId = instanceGoals[gId]
                    ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[InstanceGoal::class.java.simpleName, gId])
                val goal = organization.getInstanceGoal(goalId)
                val assignment = assignmentFactory.buildAssignment(agent, role, goal)
                return organization.addAssignment(assignment)
            }
        })
    }

    private fun parseInstanceGoal(organization: Organization, element: StartElement,
                                  specificationGoals: Map<String, UniqueId<SpecificationGoal>>, instanceGoals: MutableMap<String, UniqueId<InstanceGoal>>,
                                  list: MutableList<RunLater>) {
        /* construction of an instance goal depends on the existence of the specification goal */
        list.add(object : RunLater {
            override fun run() {
                val value = getAttributeValue(element, SPECIFICATION_ATTRIBUTE)
                val goalId = specificationGoals[value]
                    ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[SpecificationGoal::class.java.simpleName, value])
                val goal = organization.getSpecificationGoal(goalId)
                val id = idFactory.build(InstanceGoal::class.java, getAttributeValue(element, NAME_ATTRIBUTE))
                // TODO parse parameter
                val parameter = object : InstanceGoal.Parameter {
                    private val serialVersionUID = 7160662689368879207L
                }
                return build(id, instanceGoals, element, Function { entityFactory.buildInstanceGoal(it, goal, parameter) }, Consumer { organization.addInstanceGoal(it) })
            }
        })
    }

    @Throws(XMLStreamException::class)
    private fun parsePmf(organization: Organization, reader: XMLEventReader, tagName: QName, id: UniqueId<Pmf>,
                         attributes: Map<String, UniqueId<Attribute>>, list: MutableList<RunLater>) {
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                if (MODERATES_ELEMENT == name.localPart) {
                    val ids = collectChild(reader, name)
                    list.add(object : RunLater {
                        override fun run() {
                            return addRelation(id, ids, attributes, BiConsumer { pmfId, attributeId -> organization.addModerates(pmfId, attributeId) }, Attribute::class.java)
                        }
                    })
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

    @Throws(XMLStreamException::class)
    private fun parseRole(organization: Organization, reader: XMLEventReader, tagName: QName, id: UniqueId<Role>,
                          attributes: Map<String, UniqueId<Attribute>>, capabilities: Map<String, UniqueId<Capability>>,
                          characteristics: Map<String, UniqueId<Characteristic>>, goals: Map<String, UniqueId<SpecificationGoal>>, list: MutableList<RunLater>) {
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                if (ACHIEVES_ELEMENT == name.localPart) {
                    val ids = collectChild(reader, name)
                    list.add(object : RunLater {
                        override fun run() {
                            return addRelation(id, ids, goals, BiConsumer { roleId, goalId -> organization.addAchieves(roleId, goalId) }, SpecificationGoal::class.java)
                        }
                    })
                } else if (CONTAINS_ELEMENT == name.localPart) {
                    val ids = collectChild(reader, name)
                    try {
                        val value = java.lang.Double.valueOf(getAttributeValue(element, VALUE_ATTRIBUTE))
                        list.add(object : RunLater {
                            override fun run() {
                                return addRelation(id, ids, characteristics, BiConsumer { c, d -> organization.addContains(c, d, value) }, Characteristic::class.java)
                            }
                        })
                    } catch (e: NumberFormatException) {
                        throw XMLStreamException(e)
                    }

                } else if (NEEDS_ELEMENT == name.localPart) {
                    val ids = collectChild(reader, name)
                    list.add(object : RunLater {
                        override fun run() {
                            return addRelation(id, ids, attributes, BiConsumer { roleId, attributeId -> organization.addNeeds(roleId, attributeId) }, Attribute::class.java)
                        }
                    })
                } else if (REQUIRES_ELEMENT == name.localPart) {
                    val ids = collectChild(reader, name)
                    list.add(object : RunLater {
                        override fun run() {
                            return addRelation(id, ids, capabilities, BiConsumer { roleId, capabilityId -> organization.addRequires(roleId, capabilityId) }, Capability::class.java)
                        }
                    })
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

    @Throws(XMLStreamException::class)
    private fun <T : UniqueId<V>, U : UniqueId<W>, V, W> addRelation(
        entity1: T,
        ids: List<String>,
        map: Map<String, U>,
        c: BiConsumer<T, U>,
        clazz: Class<W>
    ) {
        for (id in ids) {
            val entity2 = map[id] ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[clazz.simpleName, id])
            c.accept(entity1, entity2)
        }
    }

    @Throws(XMLStreamException::class)
    private fun collectChild(reader: XMLEventReader, tagName: QName): List<String> {
        val ids = ArrayList<String>()
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                if (CHILD_ELEMENT == name.localPart) {
                    ids.add(reader.nextEvent().asCharacters().data)
                }
            } else if (event.isEndElement) {
                val element = event.asEndElement()
                if (element.name == tagName) {
                    return ids
                }
            }
        }
        throw XMLStreamException(E.MISSING_END_TAG[tagName]) // should not happen as XMLEventReader will do it for us
    }

    companion object {

        private val log = org.slf4j.LoggerFactory.getLogger(XmlParser::class.java)

        private val ROLE_DIAGRAM_ELEMENT = "RoleDiagram" //$NON-NLS-1$
        private val AGENT_ELEMENT = "Agent" //$NON-NLS-1$
        private val ATTRIBUTE_ELEMENT = "Attribute" //$NON-NLS-1$
        private val CAPABILITY_ELEMENT = "Capability" //$NON-NLS-1$
        private val CHARACTERISTIC_ELEMENT = "Characteristic" //$NON-NLS-1$
        private val INSTANCEGOAL_ELEMENT = "InstanceGoal" //$NON-NLS-1$
        private val PMF_ELEMENT = "Pmf" //$NON-NLS-1$
        private val POLICY_ELEMENT = "Policy" //$NON-NLS-1$
        private val ROLE_ELEMENT = "Role" //$NON-NLS-1$
        private val GOAL_ELEMENT = "Goal" //$NON-NLS-1$
        private val ACHIEVES_ELEMENT = "achieves" //$NON-NLS-1$
        private val ASSIGNMENT_ELEMENT = "assignment" //$NON-NLS-1$
        private val CONTAINS_ELEMENT = "contains" //$NON-NLS-1$
        private val HAS_ELEMENT = "has" //$NON-NLS-1$
        private val MODERATES_ELEMENT = "moderates" //$NON-NLS-1$
        private val NEEDS_ELEMENT = "needs" //$NON-NLS-1$
        private val POSSESSES_ELEMENT = "possesses" //$NON-NLS-1$
        private val REQUIRES_ELEMENT = "requires" //$NON-NLS-1$
        private val ID_ATTRIBUTE = "id" //$NON-NLS-1$
        private val NAME_ATTRIBUTE = "name" //$NON-NLS-1$
        private val TYPE_ATTRIBUTE = "type" //$NON-NLS-1$
        private val SPECIFICATION_ATTRIBUTE = "specification" //$NON-NLS-1$
        private val VALUE_ATTRIBUTE = "value" //$NON-NLS-1$
        private val SCORE_ATTRIBUTE = "score" //$NON-NLS-1$
        private val AGENT_ATTRIBUTE = "agent" //$NON-NLS-1$
        private val ROLE_ATTRIBUTE = "role" //$NON-NLS-1$
        private val GOAL_ATTRIBUTE = "goal" //$NON-NLS-1$
        private val CHILD_ELEMENT = "child" //$NON-NLS-1$
    }

}