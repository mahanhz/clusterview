package org.amhzing.clusterview.data.jpa.repository.stats;

import org.amhzing.clusterview.data.jpa.entity.stats.StatsHistoryEntity;
import org.amhzing.clusterview.data.jpa.entity.stats.StatsHistoryPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatsHistoryJpaRepository extends JpaRepository<StatsHistoryEntity, StatsHistoryPk> {

    List<StatsHistoryEntity> findByStatsHistoryPkClusterId(String clusterId);
}
