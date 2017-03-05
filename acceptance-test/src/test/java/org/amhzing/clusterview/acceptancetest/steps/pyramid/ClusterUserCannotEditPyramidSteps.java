package org.amhzing.clusterview.acceptancetest.steps.pyramid;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.acceptancetest.steps.page.ClusterPageSteps;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ClusterUserCannotEditPyramidSteps extends SpringSteps implements En {

    private ResponseEntity<String> response;

    public ClusterUserCannotEditPyramidSteps() {

        Then("^the cluster user is forbidden from editing the pyramid", () -> {
            Assertions.assertThat(ClusterPageSteps.getEditPyramidResponse().getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        });
    }
}
