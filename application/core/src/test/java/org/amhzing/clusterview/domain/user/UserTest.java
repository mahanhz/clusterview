package org.amhzing.clusterview.domain.user;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.Name;
import org.amhzing.clusterview.helper.JUnitParamHelper;
import org.amhzing.clusterview.helper.NoException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.amhzing.clusterview.helper.DomainModelHelper.name;
import static org.amhzing.clusterview.helper.UserDomainModelHelper.*;
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