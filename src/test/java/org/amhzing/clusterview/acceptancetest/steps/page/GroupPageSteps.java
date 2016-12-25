package org.amhzing.clusterview.acceptancetest.steps.page;

import cucumber.api.java8.En;
import org.amhzing.clusterview.acceptancetest.SpringSteps;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.amhzing.clusterview.acceptancetest.steps.access.UserLoginSteps.getLoginHeaders;

public class GroupPageSteps extends SpringSteps implements En {

    public static final String CLUSTER = "stockholm";
    public static final int GROUP_ID = 901;

    private static ResponseEntity<String> groupPageResponse;

    public GroupPageSteps() {

        When("^in the group page$", () -> {
            final HttpHeaders headers = new HttpHeaders();

            final String cookie = getLoginHeaders().getFirst("Set-Cookie");
            headers.set("Cookie", cookie);

            groupPageResponse = getTestRestTemplate().exchange("/clusterview/se/central/" + CLUSTER + "/" + GROUP_ID,
                                                               HttpMethod.GET,
                                                               new HttpEntity<>(headers),
                                                               String.class);
        });
    }

    public static ResponseEntity<String> getGroupPageResponse() {
        return groupPageResponse;
    }
}
