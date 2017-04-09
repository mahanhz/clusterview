package org.amhzing.clusterview.app.domain.model.user;

import com.google.common.collect.ImmutableList;
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
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class UserTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> expectedException,
                              final Email email,
                              final Name name,
                              final State state,
                              final List<Role> roles,
                              final List<Country.Id> countries) {

        Class<? extends Exception> actualException = NoException.class;

        try {
            User.create(email, name, state, roles, countries);
        } catch (Exception ex) {
            actualException = ex.getClass();
        }

        assertThat(actualException).isEqualTo(expectedException);
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { JUnitParamHelper.valid(), email(), name(), state(), roles(), countries() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), email(), name(), state(), roles(), null },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), email(), name(), state(), null, countries() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), email(), name(), null, roles(), countries() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), email(), null, state(), roles(), countries() },
                { JUnitParamHelper.invalidMatching(NullPointerException.class), null, name(), state(), roles(), countries() }
        };
    }

    private Email email() {
        return ImmutableEmail.of("test@example.om");
    }

    private State state() {
        return ImmutableState.of(true);
    }

    private List<Role> roles() {
        return ImmutableList.of(ImmutableRole.of("ADMIN"));
    }

    private List<Country.Id> countries() {
        return ImmutableList.of(Country.Id.create("se"));
    }
}