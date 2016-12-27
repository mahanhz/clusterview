package org.amhzing.clusterview.acceptancetest.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;
import static org.assertj.core.api.Assertions.assertThat;

public final class RestTemplateHelper {

    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";
    public static final String CSRF = "_csrf";
    public static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";

    private RestTemplateHelper() {
        // to prevent instantiation
    }

    public static HttpHeaders getHeaders(final TestRestTemplate testRestTemplate,
                                         final String uri) {
        notNull(testRestTemplate);
        notBlank(uri);

        final HttpHeaders headers = new HttpHeaders();

        final ResponseEntity<String> response = testRestTemplate.getForEntity(uri, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final String cookie = response.getHeaders().getFirst(SET_COOKIE);
        headers.set(COOKIE, cookie);

        final Document doc = Jsoup.parse(response.getBody());
        final Elements csrfField = doc.getElementsByAttributeValueContaining("name", CSRF);
        assertThat(csrfField).hasSize(1);

        headers.set(X_CSRF_TOKEN, csrfField.get(0).val());

        return headers;
    }

    public static HttpHeaders getHeaders(final TestRestTemplate testRestTemplate,
                                         final String uri,
                                         final HttpHeaders loginHeaders) {
        notNull(testRestTemplate);
        notBlank(uri);
        notNull(loginHeaders);

        final HttpHeaders headers = new HttpHeaders();

        final String cookie = loginHeaders.getFirst(SET_COOKIE);
        headers.set(COOKIE, cookie);

        final ResponseEntity<String> response = testRestTemplate.exchange(uri,
                                                                          HttpMethod.GET,
                                                                          new HttpEntity<>(headers),
                                                                          String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final Document doc = Jsoup.parse(response.getBody());
        final Elements csrfField = doc.getElementsByAttributeValueContaining("name", CSRF);
        assertThat(csrfField).isNotEmpty();

        headers.set(X_CSRF_TOKEN, csrfField.get(0).val());

        return headers;
    }

    public static HttpHeaders login(final String username,
                                    final String password,
                                    final TestRestTemplate testRestTemplate) {
        notBlank(username);
        notBlank(password);
        notNull(testRestTemplate);

        final HttpHeaders headers = getHeaders(testRestTemplate, "/login");
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.set("username", username);
        form.set("password", password);

        final ResponseEntity<String> response = testRestTemplate.exchange("/login",
                                                                          HttpMethod.POST,
                                                                          new HttpEntity<>(form, headers),
                                                                          String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().getLocation().toString()).endsWith("/clusterview/se");

        return response.getHeaders();
    }
}
