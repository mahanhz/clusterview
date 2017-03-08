package org.amhzing.clusterview.app.web.controller.rest.cache;

import org.amhzing.clusterview.app.web.model.CacheNamesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.amhzing.clusterview.app.web.CustomMediaType.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping(path = BASE_PATH + "/manage/caches", produces = APPLICATION_JSON_V1_VALUE)
public class CacheRestController {

    private CacheManager cacheManager;

    @Autowired
    public CacheRestController(final CacheManager cacheManager) {
        this.cacheManager = notNull(cacheManager);
    }

    @GetMapping(path = "/list")
    public CacheNamesModel caches() {
        final List<String> cacheNames = cacheManager.getCacheNames()
                                                    .stream()
                                                    .sorted(String::compareTo)
                                                    .collect(Collectors.toList());

        return CacheNamesModel.create(cacheNames);
    }

    @DeleteMapping(path = "/clear", consumes = APPLICATION_JSON_V1_VALUE)
    public List<String> clearCache(@Valid @RequestBody final CacheNamesModel cacheNamesModel) {

        return cacheNamesModel.getCacheNames()
                              .stream()
                              .map(this::clearCache)
                              .collect(Collectors.toList());
    }

    private String clearCache(final String name) {
        final Cache cache = cacheManager.getCache(name);
        if (cache == null) {
            return "No cache found with name " + name;
        }
        cache.clear();

        return "Cleared cache " + name;
    }
}
