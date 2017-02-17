package org.amhzing.clusterview.acceptancetest.steps.login;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.getHeaders;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginSuccessfulSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;
    private String username;
    private String password;

    @Autowired
    public LoginSuccessfulSteps(final TestRestTemplate testRestTemplate) {

        Given("^valid username \"([^\"]*)\" and password \"([^\"]*)\"$", (String username, String password) -> {
            this.username = username;
            this.password = password;
        });

        When("^login is attempted$", () -> {
            final HttpHeaders headers = getHeaders(testRestTemplate, "/login");

            final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.set("username", username);
            form.set("password", password);

            response = testRestTemplate.exchange("/login",
                                                 HttpMethod.POST,
                                                 new HttpEntity<>(form, headers),
                                                 String.class);
        });

        Then("^login is successful$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
            assertThat(response.getHeaders().getLocation().toString()).isEqualTo("http://localhost:" + super.getPort() + "/clusterview/se");
        });
    }
}
