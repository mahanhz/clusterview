package org.amhzing.clusterview.acceptancetest.group.create;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.infra.jpa.repository.TeamJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.getHeaders;
import static org.amhzing.clusterview.acceptancetest.helper.RestTemplateHelper.login;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminCreateGroupSteps extends SpringSteps implements En {

    public static final String CLUSTER = "stockholm";

    private HttpHeaders loginHeaders;
    private ResponseEntity<String> response;
    private long initialGroupsSize;

    @Autowired
    public AdminCreateGroupSteps(final TestRestTemplate testRestTemplate) {

        Given("^a logged in admin user$", () -> {
            initialGroupsSize = groupsSize(getTeamJpaRepository());
            loginHeaders = login("admin@example.com", "admin123", testRestTemplate);
        });

        When("^attempting to create a group$", () -> {
            final HttpHeaders headers = getHeaders(testRestTemplate,
                                                   "/clusteredit/se/central/" + CLUSTER + "/newgroup",
                                                   loginHeaders);
            headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.set("id", "0");
            form.set("location.coordX", "100");
            form.set("location.coordY", "100");
            form.set("coreActivities[0].id", "cc");
            form.set("coreActivities[0].name", "CC");
            form.set("coreActivities[0].quantity", "0");
            form.set("coreActivities[0].totalParticipants", "0");
            form.set("coreActivities[0].communityOfInterest", "0");
            form.set("coreActivities[1].id", "dm");
            form.set("coreActivities[1].name", "DM");
            form.set("coreActivities[1].quantity", "0");
            form.set("coreActivities[1].totalParticipants", "0");
            form.set("coreActivities[1].communityOfInterest", "0");
            form.set("coreActivities[2].id", "jyg");
            form.set("coreActivities[2].name", "JYG");
            form.set("coreActivities[2].quantity", "0");
            form.set("coreActivities[2].totalParticipants", "0");
            form.set("coreActivities[2].communityOfInterest", "0");
            form.set("coreActivities[3].id", "sc");
            form.set("coreActivities[3].name", "SC");
            form.set("coreActivities[3].quantity", "0");
            form.set("coreActivities[3].totalParticipants", "0");
            form.set("coreActivities[3].communityOfInterest", "0");
            form.set("members[0].name.firstName", "testF");
            form.set("members[0].name.lastName", "testL");

            response = testRestTemplate.exchange("/clusteredit/se/central/" + CLUSTER + "/creategroup",
                                                 HttpMethod.POST,
                                                 new HttpEntity<>(form, headers),
                                                 String.class);
        });

        Then("^the group is created$", () -> {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
            assertThat(response.getHeaders().getLocation().toString()).isEqualTo("http://localhost:" +
                                                                                 super.getPort() +
                                                                                 "/clusterview/se/central/" + CLUSTER);

            assertThat(groupsSize(getTeamJpaRepository())).isGreaterThan(initialGroupsSize);
        });
    }

    private long groupsSize(final TeamJpaRepository teamJpaRepository) {
        return teamJpaRepository.count();
    }
}
