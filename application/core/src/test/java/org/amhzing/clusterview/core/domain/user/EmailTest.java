package org.amhzing.clusterview.core.domain.user;

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
public class EmailTest {

    private static final String VALUE_WITH_WHITE_SPACE = "  test@example.com  ";

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> expectedException,
                              final String value) {

        Class<? extends Exception> actualException = NoException.class;

        try {
            ImmutableEmail.of(value);
        } catch (Exception ex) {
            actualException = ex.getClass();
        }

        assertThat(actualException).isEqualTo(expectedException);
    }

    @Test
    public void should_trim_value() {
        final ImmutableEmail email = ImmutableEmail.of(VALUE_WITH_WHITE_SPACE);

        assertThat(email.value()).isEqualTo(trim(VALUE_WITH_WHITE_SPACE));
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {JUnitParamHelper.valid(), "test@example.com"},
                {JUnitParamHelper.valid(), "test@example.se"},
                {JUnitParamHelper.valid(), "test@a.se"},
                {JUnitParamHelper.valid(), StringUtils.repeat("a", Email.MAX_LENGTH - 6) + "@a.com"},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), StringUtils.repeat("a", Email.MAX_LENGTH - 5) + "@a.com"},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "test@example"},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "test@example."},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "test@example.abcd"},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), ""},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), " "},
                {JUnitParamHelper.invalidMatching(NullPointerException.class), null}
        };
    }
}