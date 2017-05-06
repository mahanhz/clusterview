package org.amhzing.clusterview.api.compare;


import org.amhzing.clusterview.api.statistic.CourseDTO;
import org.amhzing.clusterview.util.NaturalOrderComparator;

import java.util.Comparator;

public final class CourseComparator implements Comparator<CourseDTO> {

    private static final NaturalOrderComparator COMPARATOR = new NaturalOrderComparator();

    @Override
    public int compare(final CourseDTO o1, final CourseDTO o2) {
        return COMPARATOR.compare(o1, o2);
    }
}
