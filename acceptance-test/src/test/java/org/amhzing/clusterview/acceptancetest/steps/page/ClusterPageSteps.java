package org.amhzing.clusterview.acceptancetest.steps.page;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.*;

import static org.amhzing.clusterview.acceptancetest.helper.GroupHelper.createGroupForm;
import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.getHeaders;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getInitialGroupsSize;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.assertj.core.api.Assertions.assertThat;

public class ClusterPageSteps extends SpringSteps implements En {

    public static final String CLUSTER = "stockholm";
    public static final String ANY_CLUSTER = "any";

    private static ResponseEntity<String> response;
    private static ResponseEntity<String> createGroupResponse;

    public ClusterPageSteps() {

        When("^in \"([^\"]*)\" cluster page$", (String cluster) -> {
            final String clusterToUse = cluster(cluster);

            final HttpHeaders headers = new HttpHeaders();

            final String cookie = getLoginHeaders().getFirst("Set-Cookie");
            headers.set("Cookie", cookie);

            response = getTestRestTemplate().exchange("/clusterview/se/central/" + clusterToUse,
                                                      HttpMethod.GET,
                                                      new HttpEntity<>(headers),
                                                      String.class);
        });

        When("^attempting to create a group in \"([^\"]*)\" cluster$", (String cluster) -> {
            final String clusterToUse = cluster(cluster);

            final HttpHeaders headers = getHeaders(getTestRestTemplate(),
                                                   "/clusteredit/se/central/" + clusterToUse + "/newgroup",
                                                   getLoginHeaders());

            createGroupResponse = getTestRestTemplate().exchange("/clusteredit/se/central/" + clusterToUse + "/creategroup",
                                                                 HttpMethod.POST,
                                                                 new HttpEntity<>(createGroupForm(), headers),
                                                                 String.class);
        });

        Then("^group operations are available$", () -> {
            assertThat(getClusterPageResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getClusterPageResponse().getBody());
            final Element clusterOperations = doc.getElementById("clusterOperations");

            assertThat(clusterOperations).isNotNull();
            assertThat(clusterOperations.getElementById("addNewGroup")).isNotNull();
        });

        Then("^group operations are not available$", () -> {
            assertThat(getClusterPageResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

            final Document doc = Jsoup.parse(getClusterPageResponse().getBody());
            final Element clusterOperations = doc.getElementById("clusterOperations");

            assertThat(clusterOperations).isNull();
        });

        Then("^the group is created in \"([^\"]*)\" cluster$", (String cluster) -> {
            final String clusterToUse = cluster(cluster);

            assertThat(createGroupResponse.getStatusCode()).isEqualTo(HttpStatus.FOUND);
            assertThat(createGroupResponse.getHeaders().getLocation().toString()).isEqualTo("http://localhost:" +
                                                                                                    super.getPort() +
                                                                                                    "/clusterview/se/central/" +
                                                                                                    clusterToUse);

            assertThat(groupsSize(getTeamJpaRepository())).isGreaterThan(getInitialGroupsSize());
        });
    }

    public static ResponseEntity<String> getClusterPageResponse() {
        return response;
    }

    public static ResponseEntity<String> getCreateGroupResponse() {
        return createGroupResponse;
    }

    private String cluster(final String cluster) {
        if (StringUtils.isBlank(cluster) || StringUtils.equalsIgnoreCase(cluster, ANY_CLUSTER)) {
            return CLUSTER;
        }

        return cluster;
    }
}
