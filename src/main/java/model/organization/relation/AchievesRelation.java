package model.organization.relation;

import static model.organization.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;

import com.google.inject.assistedinject.Assisted;

class AchievesRelation implements Achieves {

	private final Role role;
	private final SpecificationGoal goal;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	AchievesRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final SpecificationGoal goal) {
		checkNotNull(role, "role");
		checkNotNull(goal, "goal");
		this.role = role;
		this.goal = goal;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public SpecificationGoal getGoal() {
		return goal;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Achieves) {
			final Achieves achieves = (Achieves) object;
			return getRole().equals(achieves.getRole()) && getGoal().equals(achieves.getGoal());

		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16 | getGoal().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("%s <-> %s", getRole().getId(), getGoal().getId());
		}
		return toString;
	}

}
