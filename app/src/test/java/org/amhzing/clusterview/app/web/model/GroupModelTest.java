package org.amhzing.clusterview.app.web.model;

import com.google.common.collect.ImmutableList;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.app.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.app.helper.ClientModelHelper.locationModel;
import static org.amhzing.clusterview.app.helper.ClientModelHelper.memberModel;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class GroupModelTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String obfuscatedId,
                              final LocationModel location,
                              final MemberModel... members) {
        try {
            GroupModel.create(obfuscatedId, ImmutableList.copyOf(members), location, emptyList());
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(GroupModel.class).suppress(Warning.STRICT_INHERITANCE,
                                                           Warning.NONFINAL_FIELDS,
                                                           Warning.ALL_FIELDS_SHOULD_BE_USED).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), "3o97MmbN", locationModel(), memberModel() },
                { JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "", locationModel(), memberModel() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), null, locationModel(), memberModel() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), "3o97MmbN", locationModel(), null },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), "3o97MmbN", null, memberModel() }
        };
    }
}