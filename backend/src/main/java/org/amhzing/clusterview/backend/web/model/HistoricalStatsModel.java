package org.amhzing.clusterview.backend.web.model;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class HistoricalStatsModel {

    private ActivityStatisticModel currentStats;
    private List<DatedActivityStatisticModel> statsHistory;

    private HistoricalStatsModel(final ActivityStatisticModel currentStats, final List<DatedActivityStatisticModel> statsHistory) {
        this.currentStats = notNull(currentStats);
        this.statsHistory = notNull(statsHistory);
    }

    public static HistoricalStatsModel create(final ActivityStatisticModel currentStats, final List<DatedActivityStatisticModel> statsHistory) {
        return new HistoricalStatsModel(currentStats, statsHistory);
    }

    public ActivityStatisticModel getCurrentStats() {
        return currentStats;
    }

    public void setCurrentStats(final ActivityStatisticModel currentStats) {
        this.currentStats = currentStats;
    }

    public List<DatedActivityStatisticModel> getStatsHistory() {
        return ImmutableList.copyOf(statsHistory);
    }

    public void setStatsHistory(final List<DatedActivityStatisticModel> statsHistory) {
        this.statsHistory = statsHistory;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalStatsModel)) return false;
        final HistoricalStatsModel that = (HistoricalStatsModel) o;
        return Objects.equals(currentStats, that.currentStats) &&
                Objects.equals(statsHistory, that.statsHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentStats, statsHistory);
    }

    @Override
    public String toString() {
        return "HistoricalStatsModel{" +
                "currentStats=" + currentStats +
                ", statsHistory=" + statsHistory +
                '}';
    }
}
