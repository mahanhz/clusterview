package org.amhzing.clusterview.appui.web.model;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class ActivityModelFormatter implements Formatter<ActivityModel> {

    @Override
    public ActivityModel parse(final String id, final Locale locale) throws ParseException {
        final ActivityModel activityModel = new ActivityModel();
        activityModel.setId(id);

        return activityModel;
    }

    @Override
    public String print(final ActivityModel activityModel, final Locale locale) {
        return activityModel.getId();
    }
}
