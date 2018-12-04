package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.organization.AttributeManager
import runtimemodels.chazm.model.exceptions.EntityExistsException
import runtimemodels.chazm.model.exceptions.EntityNotExistsException
import javax.inject.Inject

internal data class DefaultAttributeManager @Inject constructor(
    private val map: MutableMap<AttributeId, Attribute>
) : AttributeManager, Map<AttributeId, Attribute> by map {
    override fun add(attribute: Attribute) {
        if (map.containsKey(attribute.id)) {
            throw EntityExistsException(attribute.id)
        }
        map[attribute.id] = attribute
    }

    override fun remove(id: AttributeId): Attribute {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }

}
