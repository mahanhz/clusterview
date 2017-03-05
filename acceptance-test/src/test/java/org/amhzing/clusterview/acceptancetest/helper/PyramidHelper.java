package org.amhzing.clusterview.acceptancetest.helper;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public final class PyramidHelper {

    private PyramidHelper() {
        // Prevent instantiation
    }

    public static MultiValueMap<String, String> createCourseStatisticsForm() {
        final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.set("courseStatistics[0].id", "1");
        form.set("courseStatistics[0].name", "Book 1");
        form.set("courseStatistics[0].quantity", "100");
        form.set("courseStatistics[1].id", "2");
        form.set("courseStatistics[1].name", "Book 2");
        form.set("courseStatistics[1].quantity", "85");

        return  form;
    }
}
