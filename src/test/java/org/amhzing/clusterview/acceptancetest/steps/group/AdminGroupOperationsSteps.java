package org.amhzing.clusterview.acceptancetest.steps.group;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.amhzing.clusterview.acceptancetest.steps.page.ClusterPageSteps.getResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminGroupOperationsSteps extends SpringSteps implements En {

    @Autowired
    public AdminGroupOperationsSteps(final TestRestTemplate testRestTemplate) {

        Then("^group operations are available$", () -> {
            assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getResponse().getBody());
            final Element groupOperations = doc.getElementById("groupOperations");

            assertThat(groupOperations).isNotNull();
            assertThat(groupOperations.getElementById("addNewGroup")).isNotNull();
        });
    }
}
