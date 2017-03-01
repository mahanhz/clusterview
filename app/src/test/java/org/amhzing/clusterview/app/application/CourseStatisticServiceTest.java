package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.Quantity;
import org.amhzing.clusterview.app.infra.repository.ClusterCourseStatisticRepository;
import org.amhzing.clusterview.app.infra.repository.CountryCourseStatisticRepository;
import org.amhzing.clusterview.app.infra.repository.RegionCourseStatisticRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.course;
import static org.amhzing.clusterview.app.helper.DomainModelHelper.courseStatistic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CourseStatisticServiceTest {

    @Mock
    private CountryCourseStatisticRepository countryStatisticRepository;
    @Mock
    private RegionCourseStatisticRepository regionStatisticRepository;
    @Mock
    private ClusterCourseStatisticRepository clusterStatisticRepository;

    private CourseStatisticService courseStatisticService;

    @Before
    public void setUp() throws Exception {
        courseStatisticService = new CourseStatisticService(countryStatisticRepository,
                                                            regionStatisticRepository,
                                                            clusterStatisticRepository);
    }

    @Test
    public void should_get_country_stats() throws Exception {

        given(countryStatisticRepository.statistics(any(Country.Id.class))).willReturn(courseStatistic());

        final CourseStatistic stats = courseStatisticService.statistics(Country.Id.create("se"));

        assertThat(stats.getCourseQuantity().get(course("2", "Book 2"))).isEqualTo(Quantity.create(40));
    }

    @Test
    public void should_get_region_stats() throws Exception {

        given(regionStatisticRepository.statistics(any(Region.Id.class))).willReturn(courseStatistic());

        final CourseStatistic stats = courseStatisticService.statistics(Region.Id.create("central"));

        assertThat(stats.getCourseQuantity().get(course("1", "Book 1"))).isEqualTo(Quantity.create(50));
    }

    @Test
    public void should_get_cluster_stats() throws Exception {

        given(clusterStatisticRepository.statistics(any(Cluster.Id.class))).willReturn(courseStatistic());

        final CourseStatistic stats = courseStatisticService.statistics(Cluster.Id.create("stockholm"));

        assertThat(stats.getCourseQuantity().get(course("3", "Book 3"))).isEqualTo(Quantity.create(30));
    }


}