package org.amhzing.clusterview.app.web.controller.rest.entry;

import com.google.common.collect.ImmutableList;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.amhzing.clusterview.app.annotation.ConditionalOnRestEnabled;
import org.amhzing.clusterview.app.user.UserUtil;
import org.amhzing.clusterview.app.web.controller.rest.cache.CacheRestController;
import org.amhzing.clusterview.app.web.controller.rest.user.UserRestController;
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

import static org.amhzing.clusterview.app.user.UserUtil.USER_COUNTRY;
import static org.amhzing.clusterview.app.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.*;
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
