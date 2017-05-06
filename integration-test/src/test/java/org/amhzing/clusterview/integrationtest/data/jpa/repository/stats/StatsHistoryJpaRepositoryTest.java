package org.amhzing.clusterview.integrationtest.data.jpa.repository.stats;

import org.amhzing.clusterview.integrationtest.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.data.jpa.entity.stats.StatsHistoryEntity;
import org.amhzing.clusterview.data.jpa.repository.stats.StatsHistoryJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class StatsHistoryJpaRepositoryTest {

    @Autowired
    private StatsHistoryJpaRepository statsHistoryJpaRepository;

    @Test
    public void should_get_history() throws Exception {
        final List<StatsHistoryEntity> clusterHistory = statsHistoryJpaRepository.findByStatsHistoryPkClusterId("stockholm");

        assertThat(clusterHistory).hasSize(JpaRepositoryHelper.INITIAL_STATS_HISTORY_STOCKHOLM_SIZE);
    }
}