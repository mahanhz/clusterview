package org.amhzing.clusterview.backend.domain.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.amhzing.clusterview.backend.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.backend.domain.model.Suffix.MAX_LENGTH;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class SuffixTest {

    private static final String VALUE_WITH_WHITE_SPACE = "  JR  ";

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String value) {
        try {
            ImmutableSuffix.of(value);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void should_trim_value() {
        final ImmutableSuffix suffix = ImmutableSuffix.of(VALUE_WITH_WHITE_SPACE);

        assertThat(suffix.value()).isEqualTo(trim(VALUE_WITH_WHITE_SPACE));
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {JUnitParamHelper.valid(), "IV"},
                {JUnitParamHelper.valid(), repeat("I", MAX_LENGTH)},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), ""},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), " "},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), repeat("I", MAX_LENGTH + 1)},
                {JUnitParamHelper.invalidMatching(NullPointerException.class), null}
        };
    }
}