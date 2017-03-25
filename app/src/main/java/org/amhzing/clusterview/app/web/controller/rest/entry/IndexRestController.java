package org.amhzing.clusterview.app.web.controller.rest.entry;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.amhzing.clusterview.app.annotation.ConditionalOnRestEnabled;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static org.amhzing.clusterview.app.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@ConditionalOnRestEnabled
@RestController
@RequestMapping(path = { "/", BASE_PATH }, produces = APPLICATION_JSON_V1_VALUE)
public class IndexRestController {

    public static final String USER_COUNTRY = "userCountry";

    @GetMapping
    public ResponseEntity<ResourceSupport> index(final HttpSession httpSession) {

        final ControllerLinkBuilder indexLink = linkTo(IndexRestController.class);

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(indexLink.withSelfRel());
        resourceSupport.add(activitiesRefDataLink());
        resourceSupport.add(coreActivitiesRefDataLink());
        resourceSupport.add(clustersRefDataLink());

        final String userCountry = (String) httpSession.getAttribute(USER_COUNTRY);
        if (StringUtils.isNotBlank(userCountry)) {
            resourceSupport.add(countryLink(userCountry));
        }

        return ResponseEntity.ok(resourceSupport);
    }
}
