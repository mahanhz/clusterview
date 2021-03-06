package org.amhzing.clusterview.web.controller.cache;

import org.amhzing.clusterview.adapter.web.api.cache.CacheDTO;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.amhzing.clusterview.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.RestControllerPath.MANAGE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.homeLink;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = MANAGE_PATH + BASE_PATH + "/caches", produces = APPLICATION_JSON_V1_VALUE)
public class CacheRestController {

    private CacheManager cacheManager;

    @Autowired
    public CacheRestController(final CacheManager cacheManager) {
        this.cacheManager = notNull(cacheManager);
    }

    @LogExecutionTime
    @GetMapping(path = "/list")
    public ResponseEntity<Resource<CacheDTO>> caches() {
        final List<String> cacheNames = cacheManager.getCacheNames()
                                                    .stream()
                                                    .sorted(String::compareTo)
                                                    .collect(Collectors.toList());

        final ControllerLinkBuilder selfLink = linkTo(methodOn(CacheRestController.class).caches());
        final ControllerLinkBuilder clearCacheLink = linkTo(CacheRestController.class).slash("clear");

        final Resource<CacheDTO> cacheDto = new Resource<>(new CacheDTO(cacheNames));
        cacheDto.add(selfLink.withSelfRel());
        cacheDto.add(homeLink());
        cacheDto.add(clearCacheLink.withRel("cache-clear"));

        return ResponseEntity.ok(cacheDto);
    }

    @DeleteMapping(path = "/clear", consumes = APPLICATION_JSON_V1_VALUE)
    public ResponseEntity<Resource<CacheDTO>> clearCache(@Valid @RequestBody final CacheDTO cacheNames) {

        final List<String> clearedCaches = cacheNames.cacheNames.stream()
                                                                .map(this::clearCache)
                                                                .collect(Collectors.toList());

        final ControllerLinkBuilder selfLink = linkTo(CacheRestController.class).slash("clear");
        final ControllerLinkBuilder cachesLink = linkTo(methodOn(CacheRestController.class).caches());

        final Resource<CacheDTO> cacheDto = new Resource<>(new CacheDTO(clearedCaches));
        cacheDto.add(selfLink.withSelfRel());
        cacheDto.add(homeLink());
        cacheDto.add(cachesLink.withRel("cache-names"));

        return ResponseEntity.ok(cacheDto);
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
