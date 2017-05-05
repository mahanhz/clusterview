package org.amhzing.clusterview.domain.user;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.helper.JUnitParamHelper;
import org.amhzing.clusterview.helper.NoException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class StateTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> expectedException,
                              final boolean value) {

        Class<? extends Exception> actualException = NoException.class;

        try {
            ImmutableState.of(value);
        } catch (Exception ex) {
            actualException = ex.getClass();
        }

        assertThat(actualException).isEqualTo(expectedException);
    }

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(ImmutableState.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {JUnitParamHelper.valid(), true},
                {JUnitParamHelper.valid(), false}
        };
    }
}