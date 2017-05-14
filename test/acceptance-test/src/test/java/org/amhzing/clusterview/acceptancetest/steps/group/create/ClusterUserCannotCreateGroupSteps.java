package org.amhzing.clusterview.acceptancetest.steps.group.create;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.acceptancetest.steps.page.ClusterPageSteps;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ClusterUserCannotCreateGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public ClusterUserCannotCreateGroupSteps() {

        Then("^the cluster user is forbidden from creating the group$", () -> {
            Assertions.assertThat(ClusterPageSteps.getCreateGroupResponse().getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        });
    }
}
