package org.amhzing.clusterview.web.controller;

import org.amhzing.clusterview.web.model.CacheNamesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
@RequestMapping(path = "/manage/caches")
public class CacheController {

    private CacheManager cacheManager;

    @Autowired
    public CacheController(final CacheManager cacheManager) {
        this.cacheManager = notNull(cacheManager);
    }

    @ModelAttribute
    public CacheNamesModel cacheNamesModel() {
        return new CacheNamesModel();
    }

    @ModelAttribute("cacheValues")
    public List<String> cacheNames() {
        return cacheManager.getCacheNames()
                           .stream()
                           .sorted(String::compareTo)
                           .collect(Collectors.toList());
    }

    @GetMapping(path = "/list")
    public String caches(final Model model) {
        return manageCacheView();
    }

    @DeleteMapping(path = "/clear", produces = MediaType.APPLICATION_JSON_VALUE)
    public String clearCache(@ModelAttribute @Valid final CacheNamesModel cacheNamesModel,
                             final BindingResult bindingResult,
                             final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return manageCacheView();
        }

        final List<String> responses = new ArrayList<>();
        cacheNamesModel.getCacheNames()
                       .stream()
                       .forEach(name -> clearCache(responses, name));

        redirectAttributes.addFlashAttribute("clearCacheResponse", responses);

        return redirectToManageCacheView();
    }

    private void clearCache(final List<String> responses, final String name) {
        final Cache cache = cacheManager.getCache(name);
        if (cache == null) {
            responses.add("No cache found with name " + name);
        }
        cache.clear();
        responses.add("Cleared cache " + name);
    }

    private String manageCacheView() {
        return "manage-cache";
    }

    private String redirectToManageCacheView() {
        return "redirect:/manage/caches/list";
    }
}
