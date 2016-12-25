package org.amhzing.clusterview.acceptancetest.steps.group.delete;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getInitialGroupsSize;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.CLUSTER;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.GROUP_ID;
import static org.assertj.core.api.Assertions.assertThat;

public class UserCannotDeleteGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public UserCannotDeleteGroupSteps() {

        When("^attempting to delete a group X$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = getLoginHeaders().getFirst("Set-Cookie");
            headers.set("Cookie", cookie);

            response = getTestRestTemplate().exchange("/clusteredit/se/central/" + CLUSTER + "/" + GROUP_ID,
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
