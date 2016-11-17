package org.amhzing.clusterview.domain.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.domain.model.FirstName.MAX_LENGTH;
import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class FirstNameTest {

    @Test
    @Parameters(method = "values")
    public void shouldValidate(final Class<? extends Exception> exception,
                              final String value)  {
        try {
            FirstName.create(value);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), "John"},
                {valid(), repeat("x", MAX_LENGTH)},
                {invalidMatching(IllegalArgumentException.class), ""},
                {invalidMatching(IllegalArgumentException.class), " "},
                {invalidMatching(IllegalArgumentException.class), repeat("x", MAX_LENGTH + 1)},
                {invalidMatching(NullPointerException.class), null}
        };
    }
}