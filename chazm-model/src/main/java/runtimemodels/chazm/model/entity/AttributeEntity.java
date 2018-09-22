package runtimemodels.chazm.model.entity;

import com.google.inject.assistedinject.Assisted;
import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.model.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class AttributeEntity extends AbstractEntity<Attribute> implements Attribute {

    private final Type type;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    AttributeEntity(@NotNull @Assisted final UniqueId<Attribute> id, @NotNull @Assisted final Type type) {
        super(id);
        this.type = type;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Attribute) {
            final Attribute attribute = (Attribute) object;
            return super.equals(attribute) && getType().equals(attribute.getType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(super.hashCode(), getType());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.ENTITY_1.get(super.toString(), getType());
        }
        return toString;
    }

    public Type getType() {
        return this.type;
    }
}
