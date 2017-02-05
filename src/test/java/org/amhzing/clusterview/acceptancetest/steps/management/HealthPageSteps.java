package org.amhzing.clusterview.acceptancetest.steps.management;

import com.jayway.jsonpath.JsonPath;
import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.assertj.core.api.Assertions.assertThat;

public class HealthPageSteps extends SpringSteps implements En {

    private static ResponseEntity<String> response;

    public HealthPageSteps() {

        When("^attempting to access the health page$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = getLoginHeaders().getFirst("Set-Cookie");
            headers.set("Cookie", cookie);

            response = getTestRestTemplate().exchange("/manage/health",
                                                      HttpMethod.GET,
                                                      new HttpEntity<>(headers),
                                                      String.class);
        });

        Then("^the health page is displayed$", () -> {
            assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getResponse().getBody());
            final String html = doc.body().html();

            final String status = JsonPath.read(html, "@.status");

            assertThat(status).isEqualToIgnoringCase("UP");
        });
    }

    public static ResponseEntity<String> getResponse() {
        return response;
    }
}
