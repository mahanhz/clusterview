package org.amhzing.clusterview.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.amhzing.clusterview.helper.JUnitParamHelper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class MiddleNameTest {

    private static final String VALUE_WITH_WHITE_SPACE = "  Sinjon  ";

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String value) {
        try {
            ImmutableMiddleName.of(value);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void should_trim_value() {
        final ImmutableMiddleName middleName = ImmutableMiddleName.of(VALUE_WITH_WHITE_SPACE);

        assertThat(middleName.value()).isEqualTo(trim(VALUE_WITH_WHITE_SPACE));
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {JUnitParamHelper.valid(), "A"},
                {JUnitParamHelper.valid(), StringUtils.repeat("a", MiddleName.MAX_LENGTH)},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), ""},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), " "},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), StringUtils.repeat("b", MiddleName.MAX_LENGTH + 1)},
                {JUnitParamHelper.invalidMatching(NullPointerException.class), null}
        };
    }
}