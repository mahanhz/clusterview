package org.amhzing.clusterview.backend.domain.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.amhzing.clusterview.backend.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.backend.domain.model.FirstName.MAX_LENGTH;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class FirstNameTest {

    private static final String VALUE_WITH_WHITE_SPACE = "  Arnold  ";

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String value) {
        try {
            ImmutableFirstName.of(value);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void should_trim_value() {
        final ImmutableFirstName firstName = ImmutableFirstName.of(VALUE_WITH_WHITE_SPACE);

        assertThat(firstName.value()).isEqualTo(trim(VALUE_WITH_WHITE_SPACE));
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {JUnitParamHelper.valid(), "John"},
                {JUnitParamHelper.valid(), repeat("x", MAX_LENGTH)},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), ""},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), " "},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), repeat("x", MAX_LENGTH + 1)},
                {JUnitParamHelper.invalidMatching(NullPointerException.class), null}
        };
    }
}