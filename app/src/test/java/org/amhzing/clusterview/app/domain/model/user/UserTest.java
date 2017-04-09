package org.amhzing.clusterview.app.domain.model.user;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Name;
import org.amhzing.clusterview.app.helper.JUnitParamHelper;
import org.amhzing.clusterview.app.helper.NoException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.name;
import static org.amhzing.clusterview.app.helper.UserDomainModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class UserTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> expectedException,
                              final Email email,
                              final Password password,
                              final Name name,
                              final State state,
                              final List<Role> roles,
                              final List<Country.Id> countries) {

        Class<? extends Exception> actualException = NoException.class;

        try {
            User.create(email, password, name, state, roles, countries);
        } catch (Exception ex) {
            actualException = ex.getClass();
        }

        assertThat(actualException).isEqualTo(expectedException);
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), email(), maskedPassword(), name(), state(), roles(), countries() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), email(), maskedPassword(), name(), state(), roles(), null },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), email(), maskedPassword(), name(), state(), null, countries() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), email(), maskedPassword(), name(), null, roles(), countries() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), email(), maskedPassword(), null, state(), roles(), countries() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), email(), null, name(), state(), roles(), countries() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), null, maskedPassword(), name(), state(), roles(), countries() }
        };
    }
}