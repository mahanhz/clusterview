package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.amhzing.clusterview.helper.DomainModelHelper.regionId;
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
                              final Region.Id... regions)  {
        try {
            Country.create(Country.Id.create(id), name, ImmutableSet.copyOf(regions));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @Test
    public void test_equals_hashcode() {
        final Country.Id id1 = Country.Id.create("SE");
        final Country.Id id2 = Country.Id.create("DK");

        final Country value = Country.create(id1, "Sweden", ImmutableSet.of(regionId()));
        final Country value2 = Country.create(id1, "Sweden", ImmutableSet.of(regionId()));
        final Country value3 = Country.create(id2, "Denmark", ImmutableSet.of(regionId()));

        assertThat(value).isEqualTo(value2);
        assertThat(value).isNotEqualTo(value3);
        assertThat(value.hashCode()).isEqualTo(value2.hashCode());
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), "SE", "Sweden", regionId() },
                {invalidMatching(IllegalArgumentException.class), "", "Sweden", regionId()},
                {invalidMatching(IllegalArgumentException.class), "DK", "", regionId()},
                {invalidMatching(NullPointerException.class), null, "Sweden", regionId()},
                {invalidMatching(NullPointerException.class), "DK", null, regionId()},
                {invalidMatching(IllegalArgumentException.class), "", "", regionId()},
                {invalidMatching(NullPointerException.class), null, null, regionId()},
                {invalidMatching(NullPointerException.class), "SE", "Sweden", null}
        };
    }
}