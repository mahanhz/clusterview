package org.amhzing.clusterview.acceptancetest.steps.group.update;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.getHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getInitialGroupsSize;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.CLUSTER;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.getGroupId;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminUpdateGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public AdminUpdateGroupSteps() {

        When("^attempting to update the group$", () -> {
            final HttpHeaders headers = getHeaders(getTestRestTemplate(),
                                                   "/clusteredit/se/central/" + CLUSTER + "/" + getGroupId(),
                                                   getLoginHeaders());
            headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.set("id", "" + getGroupId());
            form.set("location.coordX", "100");
            form.set("location.coordY", "100");
            form.set("members[0].name.firstName", "updatedFirstname");
            form.set("members[0].name.lastName", "updatedLastname");

            response = getTestRestTemplate().exchange("/clusteredit/se/central/" + CLUSTER + "/" + getGroupId(),
                                                      HttpMethod.PUT,
                                                      new HttpEntity<>(form, headers),
                                                      String.class);
        });

        Then("^the group is updated", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
            assertThat(response.getHeaders().getLocation().toString()).isEqualTo("http://localhost:" +
                                                                                 super.getPort() +
                                                                                 "/clusterview/se/central/" + CLUSTER);

            assertThat(groupsSize(getTeamJpaRepository())).isEqualTo(getInitialGroupsSize());
        });
    }
}
