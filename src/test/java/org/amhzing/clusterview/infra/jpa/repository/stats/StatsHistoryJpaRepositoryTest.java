package org.amhzing.clusterview.infra.jpa.repository.stats;

import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.infra.jpa.mapping.stats.StatsHistoryEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.INITIAL_STATS_HISTORY_STOCKHOLM_SIZE;
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

        assertThat(clusterHistory).hasSize(INITIAL_STATS_HISTORY_STOCKHOLM_SIZE);
    }
}