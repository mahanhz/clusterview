package org.amhzing.clusterview.app.domain.model;

import com.google.common.collect.ImmutableSet;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.amhzing.clusterview.app.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.region;
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

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Country.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {JUnitParamHelper.valid(), "SE", "Sweden", region() },
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "", "Sweden", region()},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "DK", "", region()},
                {JUnitParamHelper.invalidMatching(NullPointerException.class), null, "Sweden", region()},
                {JUnitParamHelper.invalidMatching(NullPointerException.class), "DK", null, region()},
                {JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "", "", region()},
                {JUnitParamHelper.invalidMatching(NullPointerException.class), null, null, region()},
                {JUnitParamHelper.invalidMatching(NullPointerException.class), "SE", "Sweden", null}
        };
    }
}