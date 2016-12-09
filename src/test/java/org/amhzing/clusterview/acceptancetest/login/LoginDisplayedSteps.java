package org.amhzing.clusterview.acceptancetest.login;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginDisplayedSteps extends SpringSteps implements En {

    private String url;
    private ResponseEntity<String> entity;

    @Autowired
    public LoginDisplayedSteps(TestRestTemplate testRestTemplate) {

        Given("^valid url$", () -> {
            url = "http://localhost:" + super.getPort() + "/clusterview/se";
        });

        When("^accessing the url$", () -> {
            entity = testRestTemplate.getForEntity(url, String.class);
        });

        Then("^login is displayed$", () -> {
            assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(entity.getBody());
            final Elements usernameField = doc.getElementsByAttributeValueContaining("name", "username");
            final Elements passswordField = doc.getElementsByAttributeValueContaining("name", "password");

            assertThat(usernameField).hasSize(1);
            assertThat(passswordField).hasSize(1);
        });
    }
}
