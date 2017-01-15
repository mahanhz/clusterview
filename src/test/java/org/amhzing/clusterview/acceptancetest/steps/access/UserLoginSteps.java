package org.amhzing.clusterview.acceptancetest.steps.access;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.HttpHeaders;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.login;
import static org.amhzing.clusterview.acceptancetest.helper.UserHelper.*;

public class UserLoginSteps extends SpringSteps implements En {

    private static HttpHeaders loginHeaders;
    private static long initialGroupsSize;

    public UserLoginSteps() {

        Given("^a logged in admin user$", () -> {
            initialGroupsSize = groupsSize(getTeamJpaRepository());
            loginHeaders = login(adminUser().getLeft(), adminUser().getRight(), getTestRestTemplate());

            setAuthentication(adminUser().getLeft(), adminUser().getRight());
        });

        Given("^a logged in user$", () -> {
            initialGroupsSize = groupsSize(getTeamJpaRepository());
            loginHeaders = login(user().getLeft(), user().getRight(), getTestRestTemplate());

            setAuthentication(user().getLeft(), user().getRight());
        });

        Given("^a logged in stockholm cluster user$", () -> {
            initialGroupsSize = groupsSize(getTeamJpaRepository());
            loginHeaders = login(stockholmUser().getLeft(), stockholmUser().getRight(), getTestRestTemplate());

            setAuthentication(stockholmUser().getLeft(), stockholmUser().getRight());
        });
    }

    public static HttpHeaders getLoginHeaders() {
        return loginHeaders;
    }

    public static long getInitialGroupsSize() {
        return initialGroupsSize;
    }
}
