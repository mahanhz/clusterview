package org.amhzing.clusterview.acceptancetest.steps.group.delete;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpStatus;

import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.getGroupPageResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminViewDeleteGroupSteps extends SpringSteps implements En {

    public AdminViewDeleteGroupSteps() {

        Then("^delete button is available$", () -> {
            assertThat(getGroupPageResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getGroupPageResponse().getBody());
            final Element deleteGroup = doc.getElementById("deleteGroup");

            assertThat(deleteGroup).isNotNull();
        });
    }
}
