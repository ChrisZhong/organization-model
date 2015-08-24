package io.github.chriszhong.model.organization.relation;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import io.github.chriszhong.model.organization.entity.Agent;
import io.github.chriszhong.model.organization.entity.AgentFactory;
import io.github.chriszhong.model.organization.entity.InstanceGoal;
import io.github.chriszhong.model.organization.entity.InstanceGoalFactory;
import io.github.chriszhong.model.organization.entity.Role;
import io.github.chriszhong.model.organization.entity.RoleFactory;
import io.github.chriszhong.model.organization.entity.SpecificationGoal;
import io.github.chriszhong.model.organization.entity.SpecificationGoalFactory;
import io.github.chriszhong.model.organization.id.IdFactory;
import io.github.chriszhong.model.organization.id.UniqueId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings({ "javadoc", "serial" })
public class AssignmentTest {

	private final Injector injector = Guice.createInjector(new RelationModule());
	private final AssignmentFactory assignmentFactory = injector.getInstance(AssignmentFactory.class);
	private final AgentFactory agentFactory = injector.getInstance(AgentFactory.class);
	private final RoleFactory roleFactory = injector.getInstance(RoleFactory.class);
	private final SpecificationGoalFactory specGoalFactory = injector.getInstance(SpecificationGoalFactory.class);
	private final InstanceGoalFactory instGoalFactory = injector.getInstance(InstanceGoalFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testAssignment() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = 2258665680559310607L;
		});
		final Assignment a1 = assignmentFactory.buildAssignment(a, r, ig);
		final Assignment a2 = assignmentFactory.buildAssignment(a, r, ig);

		assertThat(a1, is(not(nullValue())));
		assertThat(a1, is(not(sameInstance(a2))));
	}

	@Test
	public void testAssignment1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		assignmentFactory.buildAssignment(null, null, null);
	}

	@Test
	public void testAssignment2() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});

		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		assignmentFactory.buildAssignment(a, null, null);
	}

	@Test
	public void testAssignment3() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));

		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		assignmentFactory.buildAssignment(a, r, null);
	}

	@Test
	public void testGetId() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = 2402225605177079546L;
		});
		final Assignment as = assignmentFactory.buildAssignment(a, r, ig);

		assertThat(as.getId(), is(not(nullValue())));
	}

	@Test
	public void testGetAgent() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = 2797717143623840677L;
		});
		final Assignment as = assignmentFactory.buildAssignment(a, r, ig);

		assertThat(as.getAgent(), is(sameInstance(a)));

	}

	@Test
	public void testGetRole() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = -6195575726387233588L;
		});
		final Assignment as = assignmentFactory.buildAssignment(a, r, ig);

		assertThat(as.getRole(), is(sameInstance(r)));
	}

	@Test
	public void testGetGoal() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = -6525144507554869052L;
		});
		final Assignment as = assignmentFactory.buildAssignment(a, r, ig);

		assertThat(as.getGoal(), is(sameInstance(ig)));
	}

	@Test
	public void testEquals() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = 4350304843927759871L;
		});
		final Assignment a1 = assignmentFactory.buildAssignment(a, r1, ig);
		final Assignment a2 = assignmentFactory.buildAssignment(a, r2, ig);
		final Assignment a3 = assignmentFactory.buildAssignment(a, r1, ig);

		assertThat(a1, is(not(equalTo(a2))));
		assertThat(a1, is(equalTo(a3)));
		assertThat(a1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1887845516175233316L;
		});
		final Assignment a1 = assignmentFactory.buildAssignment(a, r1, ig);
		final Assignment a2 = assignmentFactory.buildAssignment(a, r2, ig);
		final Assignment a3 = assignmentFactory.buildAssignment(a, r1, ig);

		assertThat(a1.hashCode(), is(not(equalTo(a2.hashCode()))));
		assertThat(a1.hashCode(), is(equalTo(a3.hashCode())));
	}

	@Test
	public void testToString() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = 5422764931294061873L;
		});
		final Assignment a1 = assignmentFactory.buildAssignment(a, r1, ig);
		final Assignment a2 = assignmentFactory.buildAssignment(a, r2, ig);
		final Assignment a3 = assignmentFactory.buildAssignment(a, r1, ig);

		assertThat(a1.toString(), is(not(equalTo(a2.toString()))));
		assertThat(a1.toString(), is(equalTo(a3.toString())));
	}

	@Test
	public void testAssignmentId() {
		final AssignmentRelation.Id i = new AssignmentRelation.Id(idFactory.build(Agent.class, "a"), idFactory.build(Role.class, "r"), idFactory.build(
				InstanceGoal.class, "g"));

		assertThat(i, is(not(nullValue())));
	}

	@Test
	public void testAssignmentIdEquals() {
		final UniqueId<Agent> x = idFactory.build(Agent.class, "a");
		final UniqueId<Role> y1 = idFactory.build(Role.class, "r1");
		final UniqueId<Role> y2 = idFactory.build(Role.class, "r2");
		final UniqueId<InstanceGoal> z = idFactory.build(InstanceGoal.class, "g");
		final AssignmentRelation.Id i1 = new AssignmentRelation.Id(x, y1, z);
		final AssignmentRelation.Id i2 = new AssignmentRelation.Id(x, y2, z);
		final AssignmentRelation.Id i3 = new AssignmentRelation.Id(x, y1, z);

		assertThat(i1, is(not(equalTo(i2))));
		assertThat(i1, is(equalTo(i3)));
		assertThat(i1, is(not(equalTo(""))));
	}

	@Test
	public void testAssignmentIdHashCode() {
		final UniqueId<Agent> x = idFactory.build(Agent.class, "a");
		final UniqueId<Role> y1 = idFactory.build(Role.class, "r1");
		final UniqueId<Role> y2 = idFactory.build(Role.class, "r2");
		final UniqueId<InstanceGoal> z = idFactory.build(InstanceGoal.class, "g");
		final AssignmentRelation.Id i1 = new AssignmentRelation.Id(x, y1, z);
		final AssignmentRelation.Id i2 = new AssignmentRelation.Id(x, y2, z);
		final AssignmentRelation.Id i3 = new AssignmentRelation.Id(x, y1, z);

		assertThat(i1.hashCode(), is(not(equalTo(i2.hashCode()))));
		assertThat(i1.hashCode(), is(equalTo(i3.hashCode())));
	}

	@Test
	public void testAssignmentIdToString() {
		final UniqueId<Agent> x = idFactory.build(Agent.class, "a");
		final UniqueId<Role> y1 = idFactory.build(Role.class, "r1");
		final UniqueId<Role> y2 = idFactory.build(Role.class, "r2");
		final UniqueId<InstanceGoal> z = idFactory.build(InstanceGoal.class, "g");
		final AssignmentRelation.Id i1 = new AssignmentRelation.Id(x, y1, z);
		final AssignmentRelation.Id i2 = new AssignmentRelation.Id(x, y2, z);
		final AssignmentRelation.Id i3 = new AssignmentRelation.Id(x, y1, z);

		assertThat(i1.toString(), is(not(equalTo(i2.toString()))));
		assertThat(i1.toString(), is(equalTo(i3.toString())));
	}

}