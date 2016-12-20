package org.amhzing.clusterview.acceptancetest.helper;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.notNull;
import static org.assertj.core.api.Assertions.assertThat;

public final class RestTemplateHelper {

    private RestTemplateHelper() {
        // to prevent instantiation
    }

    public static HttpHeaders getHeaders(final TestRestTemplate testRestTemplate) {
        notNull(testRestTemplate);

        final HttpHeaders headers = new HttpHeaders();

        final ResponseEntity<String> page = testRestTemplate.getForEntity("/login", String.class);
        assertThat(page.getStatusCode()).isEqualTo(HttpStatus.OK);

        final String cookie = page.getHeaders().getFirst("Set-Cookie");
        headers.set("Cookie", cookie);

        final Pattern pattern = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*");

        final Matcher matcher = pattern.matcher(page.getBody());
        assertThat(matcher.matches()).as(page.getBody()).isTrue();
        headers.set("X-CSRF-TOKEN", matcher.group(1));

        return headers;
    }
}
