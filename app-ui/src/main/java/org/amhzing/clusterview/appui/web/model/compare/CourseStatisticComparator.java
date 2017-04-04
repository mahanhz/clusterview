package org.amhzing.clusterview.appui.web.model.compare;

import org.amhzing.clusterview.app.util.NaturalOrderComparator;
import org.amhzing.clusterview.appui.web.model.CourseStatisticModel;

import java.util.Comparator;

public final class CourseStatisticComparator implements Comparator<CourseStatisticModel> {

    private static final NaturalOrderComparator COMPARATOR = new NaturalOrderComparator();

    @Override
    public int compare(final CourseStatisticModel o1, final CourseStatisticModel o2) {
        return COMPARATOR.compare(o1, o2);
    }
}
