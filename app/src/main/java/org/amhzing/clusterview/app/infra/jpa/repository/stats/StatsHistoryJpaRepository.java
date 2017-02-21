package org.amhzing.clusterview.app.infra.jpa.repository.stats;

import org.amhzing.clusterview.app.infra.jpa.mapping.stats.StatsHistoryEntity;
import org.amhzing.clusterview.app.infra.jpa.mapping.stats.StatsHistoryPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatsHistoryJpaRepository extends JpaRepository<StatsHistoryEntity, StatsHistoryPk> {

    List<StatsHistoryEntity> findByStatsHistoryPkClusterId(String clusterId);
}
