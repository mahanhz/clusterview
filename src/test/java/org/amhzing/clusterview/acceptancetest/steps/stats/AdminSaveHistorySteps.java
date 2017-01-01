package org.amhzing.clusterview.acceptancetest.steps.stats;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.getHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminSaveHistorySteps extends SpringSteps implements En {

    private ResponseEntity<String> response;
    private long initialHistoryCount;

    public AdminSaveHistorySteps() {

        When("^attempting to save history$", () -> {

            initialHistoryCount = getStatsHistoryJpaRepository().count();

            final HttpHeaders headers = getHeaders(getTestRestTemplate(),
                                                   "/statsview/history/se",
                                                   getLoginHeaders());

            final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.set("name", "stockholm");

            response = getTestRestTemplate().exchange("/statsedit/history/se",
                                                      HttpMethod.POST,
                                                      new HttpEntity<>(form, headers),
                                                      String.class);
        });

        Then("^the history is saved$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
            assertThat(response.getHeaders().getLocation().toString()).isEqualTo("http://localhost:" +
                                                                                 super.getPort() +
                                                                                 "/statsview/history/se");

            assertThat(getStatsHistoryJpaRepository().count()).isGreaterThan(initialHistoryCount);
        });
    }
}
