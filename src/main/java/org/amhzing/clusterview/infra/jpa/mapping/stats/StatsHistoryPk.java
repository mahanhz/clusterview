package org.amhzing.clusterview.infra.jpa.mapping.stats;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Embeddable
public final class StatsHistoryPk implements Serializable {

    private String clusterId;
    private Date date;

    public StatsHistoryPk() {
    }

    private StatsHistoryPk(final String clusterId, final Date date) {
        this.clusterId = notBlank(clusterId);
        this.date = notNull(date);
    }

    public static StatsHistoryPk create(final String clusterId, final Date date) {
        return new StatsHistoryPk(clusterId, date);
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(final String clusterId) {
        this.clusterId = clusterId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof StatsHistoryPk)) return false;
        final StatsHistoryPk that = (StatsHistoryPk) o;
        return Objects.equals(clusterId, that.clusterId) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clusterId, date);
    }

    @Override
    public String toString() {
        return "StatsHistoryPk{" +
                "clusterId='" + clusterId + '\'' +
                ", date=" + date +
                '}';
    }
}
