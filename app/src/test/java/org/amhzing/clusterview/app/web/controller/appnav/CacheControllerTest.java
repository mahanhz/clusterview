package org.amhzing.clusterview.app.web.controller.appnav;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.security.WithMockCustomUser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import org.springframework.web.servlet.FlashMap;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CacheController.class)
@TestOffline
public class CacheControllerTest {

    public static final String CACHE_TEST_NAME = "test";
    public static final String TEST_KEY = "testKey";
    public static final String TEST_VALUE = "testValue";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private CommonModelController commonModelController;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_cache_list() throws Exception {
        given(cacheManager.getCacheNames()).willReturn(ImmutableList.of(CACHE_TEST_NAME));
        given(cacheManager.getCache(any())).willReturn(testCache());

        final ResultActions result = this.mvc.perform(get("/manage/caches/list").sessionAttr("userCountry", "se"))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Element cachesNames = doc.getElementById("cacheNames");
        assertThat(cachesNames).isNotNull();

        final Elements options = doc.getElementsByTag("option");
        assertThat(options).hasSize(1);
        assertThat(options.first().val()).isEqualTo(CACHE_TEST_NAME);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_clear_cache() throws Exception {
        given(cacheManager.getCacheNames()).willReturn(ImmutableList.of(CACHE_TEST_NAME));
        given(cacheManager.getCache(any())).willReturn(testCache());

        cacheManager.getCache(CACHE_TEST_NAME).put(TEST_KEY, TEST_VALUE);

        assertThat(cacheManager.getCache(CACHE_TEST_NAME).get(TEST_KEY).get()).isEqualTo(TEST_VALUE);

        final ResultActions result = this.mvc.perform(delete("/manage/caches/clear")
                                                              .param("cacheNames", CACHE_TEST_NAME))
                                             .andExpect(status().is3xxRedirection());

        final FlashMap flashMap = result.andReturn().getFlashMap();

        assertThat(flashMap.size()).isEqualTo(1);
        assertThat(((List) flashMap.get("clearCacheResponse")).contains("Cleared cache " + CACHE_TEST_NAME));
        assertThat(cacheManager.getCache(CACHE_TEST_NAME).get(TEST_KEY)).isNull();
    }

    private ConcurrentMapCache testCache() {
        return new ConcurrentMapCache(CACHE_TEST_NAME);
    }
}