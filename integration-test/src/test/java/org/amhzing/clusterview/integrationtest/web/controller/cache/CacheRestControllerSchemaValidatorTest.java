package org.amhzing.clusterview.integrationtest.web.controller.cache;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.integrationtest.helper.RestHelper;
import org.amhzing.clusterview.integrationtest.security.WithMockCustomUser;
import org.amhzing.clusterview.web.api.cache.CacheDTO;
import org.amhzing.clusterview.web.controller.cache.CacheRestController;
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

import static org.amhzing.clusterview.integrationtest.helper.SchemaValidationHelper.assertSuccessfulSchemaValidation;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.RestControllerPath.MANAGE_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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