package org.amhzing.clusterview.app.web.controller.rest.cache;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.api.cache.CacheDTO;
import org.amhzing.clusterview.app.helper.RestHelper;
import org.amhzing.clusterview.app.security.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.amhzing.clusterview.app.helper.SchemaValidationHelper.assertSuccessfulSchemaValidation;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.MANAGE_PATH;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(CacheRestController.class)
@TestOffline
public class CacheRestControllerSchemaValidatorTest {

    public static final String CACHE_TEST_NAME = "test";
    public static final String TEST_KEY = "testKey";
    public static final String TEST_VALUE = "testValue";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CacheManager cacheManager;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_cache_links() throws Exception {

        given(cacheManager.getCacheNames()).willReturn(ImmutableList.of(CACHE_TEST_NAME));
        given(cacheManager.getCache(any())).willReturn(testCache());

        final ResultActions result = RestHelper.get(mvc, MANAGE_PATH + BASE_PATH + "/caches/list");

        assertSuccessfulSchemaValidation(result, "CacheDTO.json");
    }

    private ConcurrentMapCache testCache() {
        return new ConcurrentMapCache(CACHE_TEST_NAME);
    }

    private CacheDTO caches() {
        return new CacheDTO(ImmutableList.of(CACHE_TEST_NAME));
    }
}