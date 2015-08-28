package io.github.chriszhong.model.organization.relation;

import io.github.chriszhong.message.M;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.relation.Needs;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.google.inject.assistedinject.Assisted;

class NeedsRelation implements Needs {

	private final Role role;
	private final Attribute attribute;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	NeedsRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final Attribute attribute) {
		this.role = role;
		this.attribute = attribute;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Needs) {
			final Needs needs = (Needs) object;
			return getRole().equals(needs.getRole()) && getAttribute().equals(needs.getAttribute());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = Objects.hash(getRole(), getAttribute());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.RELATION.get(getRole().getId(), getAttribute().getId());
		}
		return toString;
	}

}
