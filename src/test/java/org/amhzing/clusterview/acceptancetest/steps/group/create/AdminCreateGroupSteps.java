package org.amhzing.clusterview.acceptancetest.steps.group.create;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.helper.GroupHelper.createGroupForm;
import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.getHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getInitialGroupsSize;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.CLUSTER;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminCreateGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public AdminCreateGroupSteps() {

        When("^attempting to create a group$", () -> {
            final HttpHeaders headers = getHeaders(getTestRestTemplate(),
                                                   "/clusteredit/se/central/" + CLUSTER + "/newgroup",
                                                   getLoginHeaders());

            response = getTestRestTemplate().exchange("/clusteredit/se/central/" + CLUSTER + "/creategroup",
                                                      HttpMethod.POST,
                                                      new HttpEntity<>(createGroupForm(), headers),
                                                      String.class);
        });

        Then("^the group is created$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
            assertThat(response.getHeaders().getLocation().toString()).isEqualTo("http://localhost:" +
                                                                                 super.getPort() +
                                                                                 "/clusterview/se/central/" + CLUSTER);

            assertThat(groupsSize(getTeamJpaRepository())).isGreaterThan(getInitialGroupsSize());
        });
    }
}
