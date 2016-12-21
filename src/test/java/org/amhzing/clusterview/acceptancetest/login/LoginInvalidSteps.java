package org.amhzing.clusterview.acceptancetest.login;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.getHeaders;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginInvalidSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;
    private String username;
    private String password;

    @Autowired
    public LoginInvalidSteps(final TestRestTemplate testRestTemplate) {

        Given("^invalid username \"([^\"]*)\" and password \"([^\"]*)\"$", (String username, String password) -> {
            this.username = username;
            this.password = password;
        });

        When("^attempt to login$", () -> {
            final HttpHeaders headers = getHeaders(testRestTemplate, "/login");
            headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.set("username", username);
            form.set("password", password);

            response = testRestTemplate.postForEntity("/login",
                                                      new HttpEntity<>(form, headers),
                                                      String.class);
        });

        Then("^login is invalid$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
            assertThat(response.getHeaders().getLocation().toString()).isEqualTo("http://localhost:" + super.getPort() + "/login?error");
        });
    }
}
