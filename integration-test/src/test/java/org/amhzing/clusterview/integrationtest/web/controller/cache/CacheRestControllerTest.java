package org.amhzing.clusterview.integrationtest.web.controller.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.integrationtest.security.WithMockCustomUser;
import org.amhzing.clusterview.web.MediaTypes;
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

import static org.amhzing.clusterview.integrationtest.helper.RestHelper.parseJson;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.RestControllerPath.MANAGE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.REL_HOME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.Link.REL_SELF;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CacheRestController.class)
@TestOffline
public class CacheRestControllerTest {

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

        final Object document = parseJson(mvc, MANAGE_PATH + BASE_PATH + "/caches/list");

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF,
                                                   REL_HOME,
                                                   "cache-clear");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_clear_cache() throws Exception {

        final ObjectMapper mapper = new ObjectMapper();
        final CacheDTO cacheDto = caches();
        final String json = mapper.writeValueAsString(cacheDto);

        given(cacheManager.getCacheNames()).willReturn(ImmutableList.of(CACHE_TEST_NAME));
        given(cacheManager.getCache(any())).willReturn(testCache());

        final ResultActions result = mvc.perform(delete(MANAGE_PATH + BASE_PATH + "/caches/clear")
                                                         .contentType(MediaTypes.APPLICATION_JSON_V1)
                                                         .content(json))
                                        .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        final String output = JsonPath.read(document, "$.cacheNames[0]");

        assertThat(output).isEqualTo("Cleared cache " + CACHE_TEST_NAME);
    }

    private ConcurrentMapCache testCache() {
        return new ConcurrentMapCache(CACHE_TEST_NAME);
    }

    private CacheDTO caches() {
        return new CacheDTO(ImmutableList.of(CACHE_TEST_NAME));
    }
}