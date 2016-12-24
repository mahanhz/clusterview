package org.amhzing.clusterview.acceptancetest.steps.group;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.steps.userlogin.UserLoginSteps.getLoginHeaders;
import static org.assertj.core.api.Assertions.assertThat;

public class UserNotAuthorizedCreateGroupSteps extends SpringSteps implements En {

    public static final String CLUSTER = "stockholm";

    private ResponseEntity<String> response;

    @Autowired
    public UserNotAuthorizedCreateGroupSteps(final TestRestTemplate testRestTemplate) {

        When("^attempting to access the new group page$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = getLoginHeaders().getFirst("Set-Cookie");
            headers.set("Cookie", cookie);

            response = testRestTemplate.exchange("/clusteredit/se/central/" + CLUSTER + "/newgroup",
                                                                              HttpMethod.GET,
                                                                              new HttpEntity<>(headers),
                                                                              String.class);
        });

        Then("^the user is forbidden", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        });
    }
}
