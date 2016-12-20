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

public class LoginSuccessfulSteps extends SpringSteps implements En {

    private ResponseEntity<String> entity;
    private String username;
    private String password;
    private String url = "http://localhost:" + super.getPort() + "/clusterview/se";

    @Autowired
    public LoginSuccessfulSteps(final TestRestTemplate testRestTemplate) {

        Given("^valid username \"([^\"]*)\" and password \"([^\"]*)\"$", (String username, String password) -> {
            this.username = username;
            this.password = password;
        });

        When("^login is attempted$", () -> {
            final HttpHeaders headers = getHeaders(testRestTemplate);
            headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.set("username", username);
            form.set("password", password);

            entity = testRestTemplate.exchange("/login",
                                               HttpMethod.POST,
                                               new HttpEntity<>(form, headers),
                                               String.class);
        });

        Then("^login is successful$", () -> {
            assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
            assertThat(entity.getHeaders().getLocation().toString()).isEqualTo("http://localhost:" + super.getPort() + "/clusterview/se");
        });
    }
}
