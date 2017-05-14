package org.amhzing.clusterview.acceptancetest.steps.group.update;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.getHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.CLUSTER;
import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.getObfuscatedId;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminUpdateGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public AdminUpdateGroupSteps() {

        When("^attempting to update the group$", () -> {
            final HttpHeaders headers = getHeaders(getTestRestTemplate(),
                                                   "/clusteredit/se/central/" + CLUSTER + "/" + getObfuscatedId(),
                                                   UserLoginSteps.getLoginHeaders());

            final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.set("obfuscatedId", getObfuscatedId());
            form.set("location.coordX", "100");
            form.set("location.coordY", "100");
            form.set("members[0].name.firstName", "updatedFirstname");
            form.set("members[0].name.lastName", "updatedLastname");

            response = getTestRestTemplate().exchange("/clusteredit/se/central/" + CLUSTER + "/" + getObfuscatedId(),
                                                      HttpMethod.PUT,
                                                      new HttpEntity<>(form, headers),
                                                      String.class);
        });

        Then("^the group is updated", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
            assertThat(response.getHeaders().getLocation().toString()).isEqualTo("http://localhost:" +
                                                                                 super.getPort() +
                                                                                 "/clusterview/se/central/" + CLUSTER);

            assertThat(groupsSize(getTeamJpaRepository())).isEqualTo(UserLoginSteps.getInitialGroupsSize());
        });
    }
}
