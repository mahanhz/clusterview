package org.amhzing.clusterview.web.api.compare;


import org.amhzing.clusterview.infra.util.NaturalOrderComparator;
import org.amhzing.clusterview.web.api.statistic.CourseDTO;

import java.util.Comparator;

public final class CourseComparator implements Comparator<CourseDTO> {

    private static final NaturalOrderComparator COMPARATOR = new NaturalOrderComparator();

    @Override
    public int compare(final CourseDTO o1, final CourseDTO o2) {
        return COMPARATOR.compare(o1, o2);
    }
}
