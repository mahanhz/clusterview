package org.amhzing.clusterview.domain.model;

import static org.apache.commons.lang3.Validate.notBlank;

public enum Activity {

    STUDY_CIRCLE("SC", "Study Circle"),
    DEVOTIONAL_MEETING("DM", "Devotional Meeting"),
    JUNIOR_YOUTH_GROUP("JYG", "Junior Youth Group"),
    CHILDRENS_CLASS("CC", "Childrens class"),
    HOME_VISIT("HV", "Home Visit");

    private final String code;
    private final String name;

    Activity(final String code, final String name) {
        this.code = notBlank(code);
        this.name = notBlank(name);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
