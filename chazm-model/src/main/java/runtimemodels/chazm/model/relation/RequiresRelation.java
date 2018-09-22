package runtimemodels.chazm.model.relation;

import com.google.inject.assistedinject.Assisted;
import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Requires;
import runtimemodels.chazm.model.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class RequiresRelation implements Requires {

    private final Role role;
    private final Capability capability;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    RequiresRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final Capability capability) {
        this.role = role;
        this.capability = capability;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Requires) {
            final Requires requires = (Requires) object;
            return getRole().equals(requires.getRole()) && getCapability().equals(requires.getCapability());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getRole(), getCapability());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.RELATION.get(getRole().getId(), getCapability().getId());
        }
        return toString;
    }

    public Role getRole() {
        return this.role;
    }

    public Capability getCapability() {
        return this.capability;
    }
}
