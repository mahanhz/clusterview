package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.helper.DomainModelHelper.region;
import static org.amhzing.clusterview.helper.JUnitParamHelper.invalidMatching;
import static org.amhzing.clusterview.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CountryTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String id,
                              final String name,
                              final Region... regions)  {
        try {
            Country.create(Country.Id.create(id), name, ImmutableSet.copyOf(regions));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), "SE", "Sweden", region() },
                {invalidMatching(IllegalArgumentException.class), "", "Sweden", region()},
                {invalidMatching(IllegalArgumentException.class), "DK", "", region()},
                {invalidMatching(NullPointerException.class), null, "Sweden", region()},
                {invalidMatching(NullPointerException.class), "DK", null, region()},
                {invalidMatching(IllegalArgumentException.class), "", "", region()},
                {invalidMatching(NullPointerException.class), null, null, region()},
                {invalidMatching(NullPointerException.class), "SE", "Sweden", null}
        };
    }
}