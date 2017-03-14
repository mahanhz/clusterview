package org.amhzing.clusterview.app.domain.model.user;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.amhzing.clusterview.app.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.app.domain.model.user.Password.MIN_LENGTH;
import static org.amhzing.clusterview.app.domain.model.user.Password.MAX_LENGTH;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class PasswordTest {

    private static final String VALUE_WITH_WHITE_SPACE = "  MyPassword  ";

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String value) {
        try {
            ImmutablePassword.of(value);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void should_trim_value() {
        final ImmutablePassword password = ImmutablePassword.of(VALUE_WITH_WHITE_SPACE);

        assertThat(password.value()).isEqualTo(trim(VALUE_WITH_WHITE_SPACE));
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {JUnitParamHelper.valid(), "ChangeMe"},
                {JUnitParamHelper.valid(), repeat("x", MIN_LENGTH)},
                {JUnitParamHelper.valid(), repeat("x", MAX_LENGTH)},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), ""},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), " "},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), repeat("x", MIN_LENGTH - 1)},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), repeat("x", MAX_LENGTH + 1)},
                {JUnitParamHelper.invalidMatching(NullPointerException.class), null}
        };
    }
}