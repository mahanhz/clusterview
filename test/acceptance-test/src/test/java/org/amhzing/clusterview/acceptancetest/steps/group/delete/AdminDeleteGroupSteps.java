package org.amhzing.clusterview.acceptancetest.steps.group.delete;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.getHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getInitialGroupsSize;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.CLUSTER;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.getObfuscatedId;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminDeleteGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public AdminDeleteGroupSteps() {

        When("^attempting to delete the group$", () -> {
            final HttpHeaders headers = getHeaders(getTestRestTemplate(),
                                                   "/clusterview/se/central/" + CLUSTER + "/" + getObfuscatedId(),
                                                   getLoginHeaders());

            response = getTestRestTemplate().exchange("/clusteredit/se/central/" + CLUSTER + "/" + getObfuscatedId(),
                                                                 HttpMethod.DELETE,
                                                                 new HttpEntity<>(headers),
                                                                 String.class);
        });

        Then("^the group is deleted$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
            assertThat(response.getHeaders().getLocation().toString()).isEqualTo("http://localhost:" +
                                                                                 super.getPort() +
                                                                                 "/clusterview/se/central/" + CLUSTER);

            assertThat(groupsSize(getTeamJpaRepository())).isLessThan(getInitialGroupsSize());
        });
    }
}
