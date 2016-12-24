package org.amhzing.clusterview.acceptancetest.steps.group;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.steps.userlogin.UserLoginSteps.getLoginHeaders;
import static org.assertj.core.api.Assertions.assertThat;

public class UserCannotCreateGroupSteps extends SpringSteps implements En {

    public static final String CLUSTER = "stockholm";

    private ResponseEntity<String> response;

    @Autowired
    public UserCannotCreateGroupSteps(final TestRestTemplate testRestTemplate) {

        When("^in the cluster page$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = getLoginHeaders().getFirst("Set-Cookie");
            headers.set("Cookie", cookie);

            response = testRestTemplate.exchange("/clusterview/se/central/" + CLUSTER,
                                                                              HttpMethod.GET,
                                                                              new HttpEntity<>(headers),
                                                                              String.class);
        });

        Then("^group operation are not available$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(response.getBody());
            final Element groupOperations = doc.getElementById("groupOperations");

            assertThat(groupOperations).isNull();
        });
    }
}
