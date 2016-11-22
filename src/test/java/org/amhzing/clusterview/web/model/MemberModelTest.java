package org.amhzing.clusterview.web.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.domain.model.Capability;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.ImmutableSet.of;
import static org.amhzing.clusterview.helper.ClientModelHelper.activityModel;
import static org.amhzing.clusterview.helper.ClientModelHelper.nameModel;
import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class MemberModelTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final long id,
                              final NameModel name,
                              final CapabilityModel capability,
                              final CommitmentModel commitment) {
        try {
            MemberModel.create(id, name, capability, commitment);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(MemberModel.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), 123L, nameModel(), CapabilityModel.create(of(activityModel())), CommitmentModel.create(of(activityModel())) },
                { invalidMatching(NullPointerException.class), 123L, null, CapabilityModel.create(of(activityModel())), CommitmentModel.create(of(activityModel())) }
        };
    }
}