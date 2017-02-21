package org.amhzing.clusterview.app.web.controller.cache;

import org.amhzing.clusterview.backend.web.controller.cache.CacheRestController;
import org.amhzing.clusterview.backend.web.model.CacheNamesModel;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
@RequestMapping(path = "/manage/caches")
public class CacheController {

    private CacheRestController cacheRestController;

    @Autowired
    public CacheController(final CacheRestController cacheRestController) {
        this.cacheRestController = notNull(cacheRestController);
    }

    @ModelAttribute
    public CacheNamesModel cacheNamesModel() {
        return new CacheNamesModel();
    }

    @ModelAttribute("cacheValues")
    public List<String> cacheNames() {
        return cacheRestController.caches().getCacheNames();
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

        final List<String> responses = cacheRestController.clearCache(cacheNamesModel);

        redirectAttributes.addFlashAttribute("clearCacheResponse", responses);

        return redirectToManageCacheView();
    }

    private String manageCacheView() {
        return "manage-cache";
    }

    private String redirectToManageCacheView() {
        return "redirect:/manage/caches/list";
    }
}
