package org.amhzing.clusterview.appui.web.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.amhzing.clusterview.appui.helper.JUnitParamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ActivityModelTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> exception,
                              final String id,
                              final String name) {
        try {
            ActivityModel.create(id, name);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), "SC", "Study Circle" },
                { JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "", "Study Circle" },
                { JUnitParamHelper.invalidMatching(IllegalArgumentException.class), "dm", "" },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), null, null }
        };
    }
}