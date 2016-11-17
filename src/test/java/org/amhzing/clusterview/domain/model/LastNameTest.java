package org.amhzing.clusterview.domain.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.domain.model.LastName.MAX_LENGTH;
import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class LastNameTest {

    @Test
    @Parameters(method = "values")
    public void shouldValidate(final Class<? extends Exception> exception,
                               final String value)  {
        try {
            LastName.create(value);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), "Doe"},
                {valid(), repeat("a", MAX_LENGTH)},
                {invalidMatching(IllegalArgumentException.class), ""},
                {invalidMatching(IllegalArgumentException.class), " "},
                {invalidMatching(IllegalArgumentException.class), repeat("b", MAX_LENGTH + 1)},
                {invalidMatching(NullPointerException.class), null}
        };
    }
}