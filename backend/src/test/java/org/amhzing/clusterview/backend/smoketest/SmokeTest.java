package org.amhzing.clusterview.backend.smoketest;

import org.amhzing.clusterview.backend.BackendApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class SmokeTest {

    @Value("${server.base-uri}")
    private String baseUri;

    @Value("${management.context-path}")
    private String managementContextPath;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void healthStatus() {
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(getUrl(), Map.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        final Map body = entity.getBody();
        assertTrue(body.containsKey("status"));
        assertEquals(body.get("status"), "UP");
    }

    private String getUrl() {
        return baseUri + "/" + managementContextPath + "/health";
    }
}