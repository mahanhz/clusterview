package org.amhzing.clusterview.app.api.compare;

import org.amhzing.clusterview.app.api.statistic.CourseDTO;
import org.amhzing.clusterview.app.util.NaturalOrderComparator;

import java.util.Comparator;

public final class CourseComparator implements Comparator<CourseDTO> {

    private static final NaturalOrderComparator COMPARATOR = new NaturalOrderComparator();

    @Override
    public int compare(final CourseDTO o1, final CourseDTO o2) {
        return COMPARATOR.compare(o1, o2);
    }
}
