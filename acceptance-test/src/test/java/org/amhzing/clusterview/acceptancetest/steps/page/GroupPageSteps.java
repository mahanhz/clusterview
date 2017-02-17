package org.amhzing.clusterview.acceptancetest.steps.page;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.web.adapter.Obfuscator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.amhzing.clusterview.acceptancetest.helper.GroupHelper.teamEntity;
import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;
import static org.amhzing.clusterview.infra.repository.GroupFactory.convertTeam;

public class GroupPageSteps extends SpringSteps implements En {

    public static final String CLUSTER = "stockholm";

    private static ResponseEntity<String> groupPageResponse;

    private static Group group;

    private static String obfuscatedId;

    public GroupPageSteps() {

        Given("^a group$", () -> {
            final TeamEntity savedTeam = getTeamJpaRepository().save(teamEntity(getClusterJpaRepository(), CLUSTER));
            group = convertTeam(savedTeam);
        });

        When("^in the group page$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = getLoginHeaders().getFirst("Set-Cookie");
            headers.set("Cookie", cookie);

            obfuscatedId = Obfuscator.obfuscate(getGroupId());
            groupPageResponse = getTestRestTemplate().exchange("/clusterview/se/central/" + CLUSTER + "/" + obfuscatedId,
                                                               HttpMethod.GET,
                                                               new HttpEntity<>(headers),
                                                               String.class);
        });
    }

    public static ResponseEntity<String> getGroupPageResponse() {
        return groupPageResponse;
    }

    public static long getGroupId() {
        return group.getId().getId();
    }

    public static String getObfuscatedId() {
        return obfuscatedId;
    }
}
