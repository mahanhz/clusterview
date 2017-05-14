package org.amhzing.clusterview.acceptancetest.steps.group.delete;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.acceptancetest.steps.page.GroupPageSteps;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCannotViewDeleteGroupSteps extends SpringSteps implements En {

    public UserCannotViewDeleteGroupSteps() {

        Then("^delete button is not available$", () -> {
            Assertions.assertThat(GroupPageSteps.getGroupPageResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(GroupPageSteps.getGroupPageResponse().getBody());
            final Element deleteGroup = doc.getElementById("deleteGroup");

            assertThat(deleteGroup).isNull();
        });
    }
}
