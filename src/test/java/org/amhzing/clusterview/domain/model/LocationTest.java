package org.amhzing.clusterview.domain.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class LocationTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final int x,
                              final int y) {
        try {
            Location.create(x, y);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void test_equals_hashcode() {
        final Location value = Location.create(10, 20);
        final Location value2 = Location.create(10, 20);
        final Location value3 = Location.create(50, 200);

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), 100, 50}
        };
    }
}