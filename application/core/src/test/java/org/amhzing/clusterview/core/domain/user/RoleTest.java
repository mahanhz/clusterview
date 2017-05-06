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
public class RoleTest {

    private static final String VALUE_WITH_WHITE_SPACE = "  ROLE_ADMIN  ";

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> expectedException,
                              final String value) {

        Class<? extends Exception> actualException = NoException.class;

        try {
            ImmutableRole.of(value);
        } catch (Exception ex) {
            actualException = ex.getClass();
        }

        assertThat(actualException).isEqualTo(expectedException);
    }

    @Test
    public void should_trim_value() {
        final ImmutableRole role = ImmutableRole.of(VALUE_WITH_WHITE_SPACE);

        assertThat(role.value()).isEqualTo(trim(VALUE_WITH_WHITE_SPACE));
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {JUnitParamHelper.valid(), "ROLE_USER"},
                {JUnitParamHelper.valid(), StringUtils.repeat("a", Role.MAX_LENGTH)},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), StringUtils.repeat("a", Role.MAX_LENGTH + 1)},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), ""},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), " "},
                {JUnitParamHelper.invalidMatching(NullPointerException.class), null}
        };
    }
}