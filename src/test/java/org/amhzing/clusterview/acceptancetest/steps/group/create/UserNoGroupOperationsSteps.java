package org.amhzing.clusterview.acceptancetest.steps.group.create;

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

public class UserNoGroupOperationsSteps extends SpringSteps implements En {

    public static final String CLUSTER = "stockholm";

    @Autowired
    public UserNoGroupOperationsSteps(final TestRestTemplate testRestTemplate) {

        Then("^group operations are not available$", () -> {
            assertThat(getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getResponse().getBody());
            final Element groupOperations = doc.getElementById("groupOperations");

            assertThat(groupOperations).isNull();
        });
    }
}
