package runtimemodels.chazm.model.relation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Pmf;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Uses;
import runtimemodels.chazm.model.entity.PmfFactory;
import runtimemodels.chazm.model.entity.RoleFactory;
import runtimemodels.chazm.model.id.IdFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class UsesRelationTest {

    private final Injector injector = Guice.createInjector(new RelationModule());
    private final UsesFactory usesFactory = injector.getInstance(UsesFactory.class);
    private final RoleFactory roleFactory = injector.getInstance(RoleFactory.class);
    private final PmfFactory pmfFactory = injector.getInstance(PmfFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testUsesRelationFactory() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Pmf p = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p"));
        final Uses us1 = usesFactory.buildUses(r, p);
        final Uses us2 = usesFactory.buildUses(r, p);

        assertThat(us1, is(not(nullValue())));
        assertThat(us1, is(not(sameInstance(us2))));
    }

    @Test
    @Ignore
    public void testUsesRelationFactoryWithNullRoleAndNullPmf() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of UsesRelation.<init>(UsesRelation.java:23) is not @Nullable"),
//                containsString("2nd parameter of UsesRelation.<init>(UsesRelation.java:23) is not @Nullable")
//        ));

        usesFactory.buildUses(null, null);
    }

    @Test
    @Ignore
    public void testUsesRelationFactoryWithNullPmf() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));

        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of UsesRelation.<init>(UsesRelation.java:23) is not @Nullable"));

        usesFactory.buildUses(r, null);
    }

    @Test
    public void testGetRole() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Pmf p = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p"));
        final Uses us = usesFactory.buildUses(r, p);

        assertThat(us.getRole(), is(sameInstance(r)));
    }

    @Test
    public void testGetPmf() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Pmf p = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p"));
        final Uses us = usesFactory.buildUses(r, p);

        assertThat(us.getPmf(), is(sameInstance(p)));
    }

    @Test
    public void testEquals() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Pmf p = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p"));
        final Uses us1 = usesFactory.buildUses(r1, p);
        final Uses us2 = usesFactory.buildUses(r2, p);
        final Uses us3 = usesFactory.buildUses(r1, p);

        assertThat(us1, is(not(equalTo(us2))));
        assertThat(us1, is(equalTo(us3)));
        assertThat(us1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Pmf p = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p"));
        final Uses us1 = usesFactory.buildUses(r1, p);
        final Uses us2 = usesFactory.buildUses(r2, p);
        final Uses us3 = usesFactory.buildUses(r1, p);

        assertThat(us1.hashCode(), is(not(equalTo(us2.hashCode()))));
        assertThat(us1.hashCode(), is(equalTo(us3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Pmf p = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p"));
        final Uses us1 = usesFactory.buildUses(r1, p);
        final Uses us2 = usesFactory.buildUses(r2, p);
        final Uses us3 = usesFactory.buildUses(r1, p);

        assertThat(us1.toString(), is(not(equalTo(us2.toString()))));
        assertThat(us1.toString(), is(equalTo(us3.toString())));
    }

}