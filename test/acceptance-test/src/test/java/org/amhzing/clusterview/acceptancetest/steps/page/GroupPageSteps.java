package org.amhzing.clusterview.acceptancetest.steps.page;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.amhzing.clusterview.acceptancetest.helper.GroupHelper;
import org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps;
import org.amhzing.clusterview.adapter.web.Obfuscator;
import org.amhzing.clusterview.core.domain.Group;
import org.amhzing.clusterview.data.jpa.entity.TeamEntity;
import org.amhzing.clusterview.data.repository.GroupFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class GroupPageSteps extends SpringSteps implements En {

    public static final String CLUSTER = "stockholm";

    private static ResponseEntity<String> groupPageResponse;

    private static Group group;

    private static String obfuscatedId;

    public GroupPageSteps() {

        Given("^a group$", () -> {
            final TeamEntity savedTeam = getTeamJpaRepository().save(GroupHelper.teamEntity(getClusterJpaRepository(), CLUSTER));
            group = GroupFactory.convertTeam(savedTeam);
        });

        When("^in the group page$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = UserLoginSteps.getLoginHeaders().getFirst("Set-Cookie");
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
