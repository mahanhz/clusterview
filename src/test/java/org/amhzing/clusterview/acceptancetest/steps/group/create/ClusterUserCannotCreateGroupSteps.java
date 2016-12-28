package org.amhzing.clusterview.acceptancetest.steps.group.create;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.helper.GroupHelper.createGroupForm;
import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.getHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.assertj.core.api.Assertions.assertThat;

public class ClusterUserCannotCreateGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public ClusterUserCannotCreateGroupSteps() {

        When("^attempting to create a group in \"([^\"]*)\"$", (String cluster) -> {
            final HttpHeaders headers = getHeaders(getTestRestTemplate(),
                                                   "/clusteredit/se/central/" + cluster + "/newgroup",
                                                   getLoginHeaders());

            response = getTestRestTemplate().exchange("/clusteredit/se/central/" + cluster + "/creategroup",
                                                      HttpMethod.POST,
                                                      new HttpEntity<>(createGroupForm(), headers),
                                                      String.class);
        });

        Then("^the cluster user is forbidden from creating the group$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        });
    }
}
