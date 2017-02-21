package org.amhzing.clusterview.backend.web.model;

import com.google.common.collect.ImmutableList;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.backend.helper.ClientModelHelper;
import org.amhzing.clusterview.backend.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.backend.helper.ClientModelHelper.groupModel;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ClusterModelTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final GroupModel... groups) {
        try {
            ClusterModel.create(ImmutableList.copyOf(groups));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(ClusterModel.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), ClientModelHelper.groupModel() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), null }
        };
    }
}