package org.amhzing.clusterview.acceptancetest.steps.userlogin;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.infra.jpa.repository.TeamJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.login;
import static org.amhzing.clusterview.acceptancetest.helper.UserHelper.adminUser;
import static org.amhzing.clusterview.acceptancetest.helper.UserHelper.user;

public class UserLoginSteps extends SpringSteps implements En {

    private static HttpHeaders loginHeaders;
    private static long initialGroupsSize;

    @Autowired
    public UserLoginSteps(final TestRestTemplate testRestTemplate) {

        Given("^a logged in admin user$", () -> {
            initialGroupsSize = groupsSize(getTeamJpaRepository());
            loginHeaders = login(adminUser().getLeft(), adminUser().getRight(), testRestTemplate);
        });

        Given("^a logged in user$", () -> {
            loginHeaders = login(user().getLeft(), user().getRight(), testRestTemplate);
        });
    }

    public static HttpHeaders getLoginHeaders() {
        return loginHeaders;
    }

    public static long getInitialGroupsSize() {
        return initialGroupsSize;
    }

    private long groupsSize(final TeamJpaRepository teamJpaRepository) {
        return teamJpaRepository.count();
    }
}
