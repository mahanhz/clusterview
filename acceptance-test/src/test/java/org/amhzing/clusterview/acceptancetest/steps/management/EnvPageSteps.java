package org.amhzing.clusterview.acceptancetest.steps.management;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.assertj.core.api.Assertions.assertThat;

public class EnvPageSteps extends SpringSteps implements En {

    private static ResponseEntity<String> response;

    public EnvPageSteps() {

        When("^attempting to access the env page$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = getLoginHeaders().getFirst("Set-Cookie");
            headers.set("Cookie", cookie);

            response = getTestRestTemplate().exchange("/manage/env",
                                                      HttpMethod.GET,
                                                      new HttpEntity<>(headers),
                                                      String.class);
        });

        Then("^the user is forbidden from the env page$", () -> {
            assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        });
    }

    public static ResponseEntity<String> getResponse() {
        return response;
    }
}
