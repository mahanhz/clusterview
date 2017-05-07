package org.amhzing.clusterview.web.controller.entry;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.web.annotation.ConditionalOnRestEnabled;
import org.amhzing.clusterview.web.controller.cache.CacheRestController;
import org.amhzing.clusterview.web.controller.user.UserRestController;
import org.amhzing.clusterview.web.controller.util.UserUtil;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
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

import static org.amhzing.clusterview.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.*;
import static org.amhzing.clusterview.web.controller.util.UserUtil.USER_COUNTRY;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@ConditionalOnRestEnabled
@RestController
@RequestMapping(path = { "/", BASE_PATH }, produces = APPLICATION_JSON_V1_VALUE)
public class IndexRestController {

    @LogExecutionTime
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
