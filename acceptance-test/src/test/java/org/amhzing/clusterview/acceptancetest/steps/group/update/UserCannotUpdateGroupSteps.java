package org.amhzing.clusterview.acceptancetest.steps.group.update;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper;
import org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps;
import org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCannotUpdateGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public UserCannotUpdateGroupSteps() {

        When("^attempting to access the edit group page for the group$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = UserLoginSteps.getLoginHeaders().getFirst(RestTemplateHelper.SET_COOKIE);
            headers.set(RestTemplateHelper.COOKIE, cookie);

            response = getTestRestTemplate().exchange("/clusteredit/se/central/" + GroupPageSteps.CLUSTER + "/" + GroupPageSteps.getObfuscatedId(),
                                                      HttpMethod.GET,
                                                      new HttpEntity<>(headers),
                                                      String.class);
        });

        Then("^the user is forbidden from editing the group$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        });
    }
}
