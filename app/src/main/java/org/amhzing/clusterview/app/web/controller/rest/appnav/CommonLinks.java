package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.web.controller.rest.entry.IndexRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public final class CommonLinks {

    public static final String HOME = "home";
    public static final String REGION_PREFIX = "region-";
    public static final String REL_STATS_ACTIVITY = "stats-activity";
    public static final String REL_STATS_COURSE = "stats-course";
    public static final String REL_STATS_HISTORY = "stats-history";

    private CommonLinks() {
        // Prevent instantiation
    }

    public static Link homeLink() {
        final ControllerLinkBuilder homeLink = linkTo(IndexRestController.class);
        return homeLink.withRel(HOME);
    }

    public static Link countryLink(final String country) {
        notBlank(country);

        final ControllerLinkBuilder countryLink = linkTo(methodOn(CountryRestController.class).country(country));
        return countryLink.withRel("country");
    }

    public static Link statsHistoryLink(final String country) {
        notBlank(country);

        final ControllerLinkBuilder statsHistoryLink = linkTo(methodOn(StatisticRestController.class).history(country));
        return statsHistoryLink.withRel(REL_STATS_HISTORY);
    }

    public static Link regionLink(final String country, final String region) {
        notBlank(country);
        notBlank(region);

        final ControllerLinkBuilder regionLink = linkTo(RegionRestController.class).slash(country).slash(region);
        return regionLink.withRel(REGION_PREFIX + region);
    }
}
