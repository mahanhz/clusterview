package org.amhzing.clusterview.acceptancetest.steps.page;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CachePageSteps extends SpringSteps implements En {

    private static ResponseEntity<String> response;

    public CachePageSteps() {

        When("^attempting to access the caches list page$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = UserLoginSteps.getLoginHeaders().getFirst("Set-Cookie");
            headers.set("Cookie", cookie);

            response = getTestRestTemplate().exchange("/manage/caches/list",
                                                      HttpMethod.GET,
                                                      new HttpEntity<>(headers),
                                                      String.class);
        });

        Then("^the caches list page is displayed$", () -> {
            assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getResponse().getBody());

            assertThat(doc.getElementById("cacheNames")).isNotNull();
            assertThat(doc.getElementById("clearCache")).isNotNull();
        });

        Then("^the user is forbidden from the caches list page$", () -> {
            assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        });
    }

    public static ResponseEntity<String> getResponse() {
        return response;
    }
}
