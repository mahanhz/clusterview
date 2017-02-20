package org.amhzing.clusterview.acceptancetest.steps.group.create;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.COOKIE;
import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.SET_COOKIE;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.CLUSTER;
import static org.assertj.core.api.Assertions.assertThat;

public class UserCannotCreateGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public UserCannotCreateGroupSteps() {

        When("^attempting to access the new group page$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = UserLoginSteps.getLoginHeaders().getFirst(SET_COOKIE);
            headers.set(COOKIE, cookie);

            response = getTestRestTemplate().exchange("/clusteredit/se/central/" + CLUSTER + "/newgroup",
                                                      HttpMethod.GET,
                                                      new HttpEntity<>(headers),
                                                      String.class);
        });

        Then("^the user is forbidden from creating the group$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        });
    }
}
