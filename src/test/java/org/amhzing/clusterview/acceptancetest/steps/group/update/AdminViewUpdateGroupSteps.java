package org.amhzing.clusterview.acceptancetest.steps.group.update;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpStatus;

import static org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps.getGroupPageResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminViewUpdateGroupSteps extends SpringSteps implements En {

    public AdminViewUpdateGroupSteps() {

        Then("^edit button is available$", () -> {
            assertThat(getGroupPageResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getGroupPageResponse().getBody());
            final Element editGroup = doc.getElementById("editGroup");

            assertThat(editGroup).isNotNull();
        });
    }
}
