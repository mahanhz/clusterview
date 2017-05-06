package org.amhzing.clusterview.core.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.amhzing.clusterview.core.helper.JUnitParamHelper;
import org.amhzing.clusterview.core.helper.NoException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class LastNameTest {

    private static final String VALUE_WITH_WHITE_SPACE = "  Smith  ";

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> expectedException,
                              final String value) {

        Class<? extends Exception> actualException = NoException.class;

        try {
            ImmutableLastName.of(value);
        } catch (Exception ex) {
            actualException = ex.getClass();
        }

        assertThat(actualException).isEqualTo(expectedException);
    }

    @Test
    public void should_trim_value() {
        final ImmutableLastName lastName = ImmutableLastName.of(VALUE_WITH_WHITE_SPACE);

        assertThat(lastName.value()).isEqualTo(trim(VALUE_WITH_WHITE_SPACE));
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {JUnitParamHelper.valid(), "Doe"},
                {JUnitParamHelper.valid(), StringUtils.repeat("a", LastName.MAX_LENGTH)},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), ""},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), " "},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), StringUtils.repeat("b", LastName.MAX_LENGTH + 1)},
                {JUnitParamHelper.invalidMatching(NullPointerException.class), null}
        };
    }
}