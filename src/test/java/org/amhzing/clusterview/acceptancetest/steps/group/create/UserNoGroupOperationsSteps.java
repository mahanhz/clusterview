package org.amhzing.clusterview.acceptancetest.steps.group.create;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpStatus;

import static org.amhzing.clusterview.acceptancetest.steps.page.ClusterPageSteps.getClusterPageResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class UserNoGroupOperationsSteps extends SpringSteps implements En {

    public static final String CLUSTER = "stockholm";

    public UserNoGroupOperationsSteps() {

        Then("^group operations are not available$", () -> {
            assertThat(getClusterPageResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getClusterPageResponse().getBody());
            final Element groupOperations = doc.getElementById("groupOperations");

            assertThat(groupOperations).isNull();
        });
    }
}
