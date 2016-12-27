package org.amhzing.clusterview.acceptancetest.steps.group.delete;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.COOKIE;
import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.SET_COOKIE;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getInitialGroupsSize;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.CLUSTER;
import static org.assertj.core.api.Assertions.assertThat;

public class UserCannotDeleteGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public UserCannotDeleteGroupSteps() {

        When("^attempting to manually delete group (\\d+)$", (Long groupId) -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = getLoginHeaders().getFirst(SET_COOKIE);
            headers.set(COOKIE, cookie);

            response = getTestRestTemplate().exchange("/clusteredit/se/central/" + CLUSTER + "/" + groupId,
                                                      HttpMethod.DELETE,
                                                      new HttpEntity<>(headers),
                                                      String.class);
        });

        Then("^the user is forbidden from deleting the group$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

            assertThat(groupsSize(getTeamJpaRepository())).isEqualTo(getInitialGroupsSize());
        });
    }
}
