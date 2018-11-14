package runtimemodels.chazm.model.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.InstanceGoal;
import runtimemodels.chazm.api.entity.SpecificationGoal;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.model.id.IdFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"javadoc", "serial"})
public class InstanceGoalEntityTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final SpecificationGoalFactory specificationGoalFactory = injector.getInstance(SpecificationGoalFactory.class);
    private final InstanceGoalFactory instanceGoalFactory = injector.getInstance(InstanceGoalFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testInstanceGoalFactory() {
        final SpecificationGoal sg1 = specificationGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, 1L));
        final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
        final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, sg1, new InstanceGoal.Parameter() {
        });
        final InstanceGoal g2 = instanceGoalFactory.buildInstanceGoal(i1, sg1, new InstanceGoal.Parameter() {
        });

        assertThat(g1, is(not(nullValue())));
        assertThat(g1, is(not(sameInstance(g2))));
    }

    @Test
    @Ignore
    public void testInstanceGoalFactoryWithNullIdAndNullGoalAndNullParameter() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of InstanceGoalEntity.<init>(InstanceGoalEntity.java:23) is not @Nullable"),
//                containsString("2nd parameter of InstanceGoalEntity.<init>(InstanceGoalEntity.java:23) is not @Nullable"),
//                containsString("3rd parameter of InstanceGoalEntity.<init>(InstanceGoalEntity.java:23) is not @Nullable")
//        ));

        instanceGoalFactory.buildInstanceGoal(null, null, null);
    }

    @Test
    @Ignore
    public void testInstanceGoalFactoryWithNullGoalAndNullParameter() {
        final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);

        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("2nd parameter of InstanceGoalEntity.<init>(InstanceGoalEntity.java:23) is not @Nullable"),
//                containsString("3rd parameter of InstanceGoalEntity.<init>(InstanceGoalEntity.java:23) is not @Nullable")
//        ));

        instanceGoalFactory.buildInstanceGoal(i1, null, null);
    }

    @Test
    @Ignore
    public void testInstanceGoalFactoryWithNullParameter() {
        final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
        final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);

        final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);

        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(containsString("3rd parameter of InstanceGoalEntity.<init>(InstanceGoalEntity.java:23) is not @Nullable"));

        instanceGoalFactory.buildInstanceGoal(i1, y1, null);
    }

    @Test
    public void testGetGoal() {
        final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
        final UniqueId<SpecificationGoal> x2 = idFactory.build(SpecificationGoal.class, 2L);
        final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);
        final SpecificationGoal y2 = specificationGoalFactory.buildSpecificationGoal(x2);

        final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
        final UniqueId<InstanceGoal> i2 = idFactory.build(InstanceGoal.class, 2L);
        final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, y1, new InstanceGoal.Parameter() {
        });
        final InstanceGoal g2 = instanceGoalFactory.buildInstanceGoal(i2, y2, new InstanceGoal.Parameter() {
        });

        assertThat(g1.getGoal(), is(sameInstance(y1)));
        assertThat(g1.getGoal(), is(not(sameInstance(y2))));
        assertThat(g1.getGoal(), is(not(equalTo(g2.getGoal()))));
    }

    @Test
    public void testGetParameter() {
        final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
        final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);

        final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
        final InstanceGoal.Parameter p1 = new InstanceGoal.Parameter() {
        };
        final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, y1, p1);

        assertThat(g1.getParameter(), is(sameInstance(p1)));
    }

    @Test
    public void testGetId() {
        final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
        final UniqueId<SpecificationGoal> x2 = idFactory.build(SpecificationGoal.class, 1L);
        final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);
        final SpecificationGoal y2 = specificationGoalFactory.buildSpecificationGoal(x2);

        final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
        final UniqueId<InstanceGoal> i2 = idFactory.build(InstanceGoal.class, 1L);
        final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, y1, new InstanceGoal.Parameter() {
        });
        final InstanceGoal g2 = instanceGoalFactory.buildInstanceGoal(i2, y2, new InstanceGoal.Parameter() {
        });

        assertThat(g1.getId(), is(sameInstance(i1)));
        assertThat(g1.getId(), is(not(sameInstance(g2.getId()))));
    }

    @Test
    public void testEqualsObject() {
        final SpecificationGoal sg1 = specificationGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, 1L));
        final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
        final UniqueId<InstanceGoal> i2 = idFactory.build(InstanceGoal.class, 2L);
        final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, sg1, new InstanceGoal.Parameter() {
        });
        final InstanceGoal g2 = instanceGoalFactory.buildInstanceGoal(i2, sg1, new InstanceGoal.Parameter() {
        });
        final InstanceGoal g3 = instanceGoalFactory.buildInstanceGoal(i1, sg1, new InstanceGoal.Parameter() {
        });

        assertThat(g1, is(not(equalTo(g2))));
        assertThat(g1, is(equalTo(g3)));
        assertThat(g1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final SpecificationGoal sg1 = specificationGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, 1L));
        final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
        final UniqueId<InstanceGoal> i2 = idFactory.build(InstanceGoal.class, 2L);
        final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, sg1, new InstanceGoal.Parameter() {
        });
        final InstanceGoal g2 = instanceGoalFactory.buildInstanceGoal(i2, sg1, new InstanceGoal.Parameter() {
        });
        final InstanceGoal g3 = instanceGoalFactory.buildInstanceGoal(i1, sg1, new InstanceGoal.Parameter() {
        });

        assertThat(g1.hashCode(), is(not(equalTo(g2.hashCode()))));
        assertThat(g1.hashCode(), is(equalTo(g3.hashCode())));
    }

    @Test
    public void testToString() {
        final SpecificationGoal sg1 = specificationGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, 1L));
        final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
        final UniqueId<InstanceGoal> i2 = idFactory.build(InstanceGoal.class, 2L);
        final InstanceGoal.Parameter p1 = new InstanceGoal.Parameter() {
        };
        final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, sg1, p1);
        final InstanceGoal g2 = instanceGoalFactory.buildInstanceGoal(i2, sg1, p1);
        final InstanceGoal g3 = instanceGoalFactory.buildInstanceGoal(i1, sg1, p1);

        assertThat(g1.toString(), is(not(equalTo(g2.toString()))));
        assertThat(g1.toString(), is(equalTo(g3.toString())));
    }
}