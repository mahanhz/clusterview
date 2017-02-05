package org.amhzing.clusterview.acceptancetest.steps.page;

import com.jayway.jsonpath.JsonPath;
import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.*;

import java.util.Map;

import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.assertj.core.api.Assertions.assertThat;

public class ManagementPageSteps extends SpringSteps implements En {

    private static ResponseEntity<String> response;

    public ManagementPageSteps() {

        When("^attempting to access the management page$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = getLoginHeaders().getFirst("Set-Cookie");
            headers.set("Cookie", cookie);

            response = getTestRestTemplate().exchange("/manage",
                                                      HttpMethod.GET,
                                                      new HttpEntity<>(headers),
                                                      String.class);
        });

        Then("^the management page is displayed$", () -> {
            assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getResponse().getBody());
            final String html = doc.body().html();

            final Map<String, String> links = JsonPath.read(html, "@._links");

            assertThat(links).containsKey("env");
            assertThat(links).containsKey("health");
            assertThat(links).containsKey("info");
            assertThat(links).containsKey("metrics");
        });

        Then("^the user is forbidden from the management page$", () -> {
            assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        });
    }

    public static ResponseEntity<String> getResponse() {
        return response;
    }
}
