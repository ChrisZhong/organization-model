package runtimemodels.chazm.model.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.entity.SpecificationGoal;
import runtimemodels.chazm.api.relation.Achieves;
import runtimemodels.chazm.model.entity.RoleFactory;
import runtimemodels.chazm.model.entity.SpecificationGoalFactory;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.relation.AchievesFactory;
import runtimemodels.chazm.model.relation.RelationModule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class AchievesEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final AchievesEventFactory aef = injector.getInstance(AchievesEventFactory.class);
    private final AchievesFactory af = injector.getInstance(AchievesFactory.class);
    private final RoleFactory rf = injector.getInstance(RoleFactory.class);
    private final SpecificationGoalFactory sgf = injector.getInstance(SpecificationGoalFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAchievesEventFactory() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final Achieves a = af.buildAchieves(r, sg);
        final AchievesEvent ae1 = aef.build(EventCategory.ADDED, a);
        final AchievesEvent ae2 = aef.build(EventCategory.ADDED, a);

        assertThat(ae1, is(not(nullValue())));
        assertThat(ae1, is(not(sameInstance(ae2))));
    }

    @Test
    @Ignore
    public void testAchievesEventFactoryWithNullCategoryAndNullAchieves() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of AchievesEvent.<init>(AchievesEvent.java:30) is not @Nullable"),
//                containsString("2nd parameter of AchievesEvent.<init>(AchievesEvent.java:30) is not @Nullable")
//        ));

        aef.build(null, null);
    }

    @Test
    @Ignore
    public void testAchievesEventFactoryWithNullAchieves() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage((containsString("2nd parameter of AchievesEvent.<init>(AchievesEvent.java:30) is not @Nullable")));

        aef.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetRoleId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final Achieves a = af.buildAchieves(r, sg);
        final AchievesEvent ae = aef.build(EventCategory.ADDED, a);

        assertThat(ae.getRoleId(), is(sameInstance(r.getId())));
    }

    @Test
    public void testGetGoalId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final Achieves a = af.buildAchieves(r, sg);
        final AchievesEvent ae = aef.build(EventCategory.ADDED, a);

        assertThat(ae.getGoalId(), is(sameInstance(sg.getId())));
    }

    @Test
    public void testEquals() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final SpecificationGoal g = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g"));
        final Achieves a1 = af.buildAchieves(r1, g);
        final Achieves a2 = af.buildAchieves(r2, g);
        final Achieves a3 = af.buildAchieves(r1, g);
        final AchievesEvent ae1 = aef.build(EventCategory.ADDED, a1);
        final AchievesEvent ae2 = aef.build(EventCategory.ADDED, a2);
        final AchievesEvent ae3 = aef.build(EventCategory.ADDED, a3);

        assertThat(ae1, is(not(equalTo(ae2))));
        assertThat(ae1, is(equalTo(ae3)));
        assertThat(ae1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final SpecificationGoal g = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g"));
        final Achieves a1 = af.buildAchieves(r1, g);
        final Achieves a2 = af.buildAchieves(r2, g);
        final Achieves a3 = af.buildAchieves(r1, g);
        final AchievesEvent ae1 = aef.build(EventCategory.ADDED, a1);
        final AchievesEvent ae2 = aef.build(EventCategory.ADDED, a2);
        final AchievesEvent ae3 = aef.build(EventCategory.ADDED, a3);

        assertThat(ae1.hashCode(), is(not(equalTo(ae2.hashCode()))));
        assertThat(ae1.hashCode(), is(equalTo(ae3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final SpecificationGoal g = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g"));
        final Achieves a1 = af.buildAchieves(r1, g);
        final Achieves a2 = af.buildAchieves(r2, g);
        final Achieves a3 = af.buildAchieves(r1, g);
        final AchievesEvent ae1 = aef.build(EventCategory.ADDED, a1);
        final AchievesEvent ae2 = aef.build(EventCategory.ADDED, a2);
        final AchievesEvent ae3 = aef.build(EventCategory.ADDED, a3);

        assertThat(ae1.toString(), is(not(equalTo(ae2.toString()))));
        assertThat(ae1.toString(), is(equalTo(ae3.toString())));
    }

}