package org.amhzing.clusterview.acceptancetest.steps.group.create;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.amhzing.clusterview.acceptancetest.steps.page.ClusterPageSteps.getCreateGroupResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class ClusterUserCannotCreateGroupSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public ClusterUserCannotCreateGroupSteps() {

        Then("^the cluster user is forbidden from creating the group$", () -> {
            assertThat(getCreateGroupResponse().getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        });
    }
}
