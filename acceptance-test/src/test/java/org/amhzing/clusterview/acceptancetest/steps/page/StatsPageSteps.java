package org.amhzing.clusterview.acceptancetest.steps.page;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

public class StatsPageSteps extends SpringSteps implements En {

    private static ResponseEntity<String> response;

    public StatsPageSteps() {

        When("^in the stats page$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = UserLoginSteps.getLoginHeaders().getFirst("Set-Cookie");
            headers.set("Cookie", cookie);

            response = getTestRestTemplate().exchange("/statsview/history/se",
                                                      HttpMethod.GET,
                                                      new HttpEntity<>(headers),
                                                      String.class);
        });

        Then("^save history button is available$", () -> {
            assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getResponse().getBody());
            final Element saveHistory = doc.getElementById("saveHistory");

            assertThat(saveHistory).isNotNull();
        });

        Then("^save history button is not available$", () -> {
            assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getResponse().getBody());
            final Element saveHistory = doc.getElementById("saveHistory");

            assertThat(saveHistory).isNull();
        });
    }

    public static ResponseEntity<String> getResponse() {
        return response;
    }
}
