package io.github.chriszhong.model.organization.function;

import io.github.chriszhong.model.organization.Organization;
import io.github.chriszhong.model.organization.relation.Assignment;

import java.util.Collection;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

@Singleton
class DefaultEffectiveness implements Effectiveness {

	@Override
	public double compute(@NotNull final Organization organization, @NotNull final Collection<Assignment> assignments) {
		return assignments
				.parallelStream()
				.mapToDouble(
						m -> organization.getGoodness(m.getRole().getId()).compute(organization, m.getAgent(), m.getRole(), m.getGoal(),
								organization.getAssignments())).sum();
	}

}