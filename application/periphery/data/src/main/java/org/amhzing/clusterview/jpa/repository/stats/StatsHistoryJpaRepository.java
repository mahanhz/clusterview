package org.amhzing.clusterview.jpa.repository.stats;

import org.amhzing.clusterview.jpa.entity.stats.StatsHistoryEntity;
import org.amhzing.clusterview.jpa.entity.stats.StatsHistoryPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatsHistoryJpaRepository extends JpaRepository<StatsHistoryEntity, StatsHistoryPk> {

    List<StatsHistoryEntity> findByStatsHistoryPkClusterId(String clusterId);
}
