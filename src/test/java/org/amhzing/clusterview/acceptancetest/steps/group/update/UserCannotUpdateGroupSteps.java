package org.amhzing.clusterview.acceptancetest.steps.group.update;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.COOKIE;
import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.SET_COOKIE;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.CLUSTER;
import static org.assertj.core.api.Assertions.assertThat;

public class UserCannotUpdateGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public UserCannotUpdateGroupSteps() {

        When("^attempting to access the edit group page for group (\\d+)$", (Long groupId) -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = getLoginHeaders().getFirst(SET_COOKIE);
            headers.set(COOKIE, cookie);

            response = getTestRestTemplate().exchange("/clusteredit/se/central/" + CLUSTER + "/" + groupId,
                                                      HttpMethod.GET,
                                                      new HttpEntity<>(headers),
                                                      String.class);
        });

        Then("^the user is forbidden from editing the group$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        });
    }
}
