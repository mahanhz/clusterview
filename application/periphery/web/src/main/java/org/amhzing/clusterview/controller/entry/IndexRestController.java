package org.amhzing.clusterview.controller.entry;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.annotation.ConditionalOnRestEnabled;
import org.amhzing.clusterview.controller.cache.CacheRestController;
import org.amhzing.clusterview.controller.user.UserRestController;
import org.amhzing.clusterview.user.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import static org.amhzing.clusterview.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.controller.appnav.CommonLinks.*;
import static org.amhzing.clusterview.user.UserUtil.USER_COUNTRY;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@ConditionalOnRestEnabled
@RestController
@RequestMapping(path = { "/", BASE_PATH }, produces = APPLICATION_JSON_V1_VALUE)
public class IndexRestController {

    @GetMapping
    public ResponseEntity<ResourceSupport> index(final HttpServletRequest request) {

        final ControllerLinkBuilder indexLink = linkTo(IndexRestController.class);

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(indexLink.withSelfRel());

        final String userCountry = (String) request.getAttribute(USER_COUNTRY);
        if (StringUtils.isNotBlank(userCountry)) {
            resourceSupport.add(countryLink(userCountry));
        }

        resourceSupport.add(activitiesRefDataLink());
        resourceSupport.add(coreActivitiesRefDataLink());
        resourceSupport.add(clustersRefDataLink());
        resourceSupport.add(superAdminLinks());

        return ResponseEntity.ok(resourceSupport);
    }

    private List<Link> superAdminLinks() {
        if (UserUtil.isSuperAdmin()) {
            final Link cachesLink = linkTo(methodOn(CacheRestController.class).caches()).withRel("caches");
            final Link usersLink = linkTo(methodOn(UserRestController.class).users(1)).withRel("users");

            return ImmutableList.of(cachesLink, usersLink);
        }

        return Collections.emptyList();
    }
}
