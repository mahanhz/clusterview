package org.amhzing.clusterview.domain.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CountryTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String code,
                              final String name)  {
        try {
            Country.create(code, name);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void test_equals_hashcode() {
        final Country value = Country.create("SE", "Sweden");
        final Country value2 = Country.create("SE", "Sweden");
        final Country value3 = Country.create("DK", "Denmark");

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), "SE", "Sweden"},
                {invalidMatching(IllegalArgumentException.class), "", "Sweden"},
                {invalidMatching(IllegalArgumentException.class), "DK", ""},
                {invalidMatching(NullPointerException.class), null, "Sweden"},
                {invalidMatching(NullPointerException.class), "DK", null},
                {invalidMatching(IllegalArgumentException.class), "", ""},
                {invalidMatching(NullPointerException.class), null, null}
        };
    }
}