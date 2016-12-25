package org.amhzing.clusterview.acceptancetest.steps.access;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.HttpHeaders;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.login;
import static org.amhzing.clusterview.acceptancetest.helper.UserHelper.adminUser;
import static org.amhzing.clusterview.acceptancetest.helper.UserHelper.user;

public class UserLoginSteps extends SpringSteps implements En {

    private static HttpHeaders loginHeaders;
    private static long initialGroupsSize;

    public UserLoginSteps() {

        Given("^a logged in admin user$", () -> {
            initialGroupsSize = groupsSize(getTeamJpaRepository());
            loginHeaders = login(adminUser().getLeft(), adminUser().getRight(), getTestRestTemplate());
        });

        Given("^a logged in user$", () -> {
            initialGroupsSize = groupsSize(getTeamJpaRepository());
            loginHeaders = login(user().getLeft(), user().getRight(), getTestRestTemplate());
        });
    }

    public static HttpHeaders getLoginHeaders() {
        return loginHeaders;
    }

    public static long getInitialGroupsSize() {
        return initialGroupsSize;
    }
}
