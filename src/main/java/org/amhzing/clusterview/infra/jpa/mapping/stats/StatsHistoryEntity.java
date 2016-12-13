package org.amhzing.clusterview.infra.jpa.mapping.stats;

import javax.persistence.*;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

@Entity(name = "statshistory")
public final class StatsHistoryEntity {

    public static final String QUANTITY = "quantity";
    public static final String TOTAL_PARTICIPANTS = "totalParticipants";
    public static final String COI = "coi";

    @Id
    private StatsHistoryPk statsHistoryPk;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = QUANTITY, column = @Column(name="cc_quantity")),
            @AttributeOverride(name = TOTAL_PARTICIPANTS, column = @Column(name="cc_total")),
            @AttributeOverride(name = COI, column = @Column(name="cc_coi"))})
    private CoreActivityStats cc;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = QUANTITY, column = @Column(name="dm_quantity")),
            @AttributeOverride(name = TOTAL_PARTICIPANTS, column = @Column(name="dm_total")),
            @AttributeOverride(name = COI, column = @Column(name="dm_coi"))})
    private CoreActivityStats dm;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = QUANTITY, column = @Column(name="jyg_quantity")),
            @AttributeOverride(name = TOTAL_PARTICIPANTS, column = @Column(name="jyg_total")),
            @AttributeOverride(name = COI, column = @Column(name="jyg_coi"))})
    private CoreActivityStats jyg;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = QUANTITY, column = @Column(name="sc_quantity")),
            @AttributeOverride(name = TOTAL_PARTICIPANTS, column = @Column(name="sc_total")),
            @AttributeOverride(name = COI, column = @Column(name="sc_coi"))})
    private CoreActivityStats sc;

    @Embedded
    private ActivityStats activityStats;

    public StatsHistoryEntity() {
    }

    private StatsHistoryEntity(final StatsHistoryPk statsHistoryPk, final CoreActivityStats cc,
                               final CoreActivityStats dm, final CoreActivityStats jyg,
                               final CoreActivityStats sc, final ActivityStats activityStats) {
        this.statsHistoryPk = notNull(statsHistoryPk);
        this.cc = notNull(cc);
        this.dm = notNull(dm);
        this.jyg = notNull(jyg);
        this.sc = notNull(sc);
        this.activityStats = notNull(activityStats);
    }

    public static StatsHistoryEntity create(final StatsHistoryPk statsHistoryPk, final CoreActivityStats cc,
                                            final CoreActivityStats dm, final CoreActivityStats jyg,
                                            final CoreActivityStats sc, final ActivityStats activityStats) {
        return new StatsHistoryEntity(statsHistoryPk, cc, dm, jyg, sc, activityStats);
    }

    public StatsHistoryPk getStatsHistoryPk() {
        return statsHistoryPk;
    }

    public void setStatsHistoryPk(final StatsHistoryPk statsHistoryPk) {
        this.statsHistoryPk = statsHistoryPk;
    }

    public CoreActivityStats getCc() {
        return cc;
    }

    public void setCc(final CoreActivityStats cc) {
        this.cc = cc;
    }

    public CoreActivityStats getDm() {
        return dm;
    }

    public void setDm(final CoreActivityStats dm) {
        this.dm = dm;
    }

    public CoreActivityStats getJyg() {
        return jyg;
    }

    public void setJyg(final CoreActivityStats jyg) {
        this.jyg = jyg;
    }

    public CoreActivityStats getSc() {
        return sc;
    }

    public void setSc(final CoreActivityStats sc) {
        this.sc = sc;
    }

    public ActivityStats getActivityStats() {
        return activityStats;
    }

    public void setActivityStats(final ActivityStats activityStats) {
        this.activityStats = activityStats;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof StatsHistoryEntity)) return false;
        final StatsHistoryEntity that = (StatsHistoryEntity) o;
        return Objects.equals(statsHistoryPk, that.statsHistoryPk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statsHistoryPk);
    }

    @Override
    public String toString() {
        return "StatsHistoryEntity{" +
                "statsHistoryPk=" + statsHistoryPk +
                ", cc=" + cc +
                ", dm=" + dm +
                ", jyg=" + jyg +
                ", sc=" + sc +
                ", activityStats=" + activityStats +
                '}';
    }
}
