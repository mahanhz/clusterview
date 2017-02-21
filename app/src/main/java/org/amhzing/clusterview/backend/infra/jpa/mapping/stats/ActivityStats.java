package org.amhzing.clusterview.backend.infra.jpa.mapping.stats;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public final class ActivityStats {

    private int ccTeacher;
    private int dmHost;
    private int firesideHost;
    private int jygAnimator;
    private int scTutor;

    public ActivityStats() {
    }

    private ActivityStats(final int ccTeacher, final int dmHost, final int firesideHost, final int jygAnimator, final int scTutor) {
        this.ccTeacher = ccTeacher;
        this.dmHost = dmHost;
        this.firesideHost = firesideHost;
        this.jygAnimator = jygAnimator;
        this.scTutor = scTutor;
    }

    public static ActivityStats create(final int ccTeacher, final int dmHost, final int firesideHost, final int jygAnimator, final int scTutor) {
        return new ActivityStats(ccTeacher, dmHost, firesideHost, jygAnimator, scTutor);
    }

    public int getCcTeacher() {
        return ccTeacher;
    }

    public void setCcTeacher(final int ccTeacher) {
        this.ccTeacher = ccTeacher;
    }

    public int getDmHost() {
        return dmHost;
    }

    public void setDmHost(final int dmHost) {
        this.dmHost = dmHost;
    }

    public int getFiresideHost() {
        return firesideHost;
    }

    public void setFiresideHost(final int firesideHost) {
        this.firesideHost = firesideHost;
    }

    public int getJygAnimator() {
        return jygAnimator;
    }

    public void setJygAnimator(final int jygAnimator) {
        this.jygAnimator = jygAnimator;
    }

    public int getScTutor() {
        return scTutor;
    }

    public void setScTutor(final int scTutor) {
        this.scTutor = scTutor;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityStats)) return false;
        final ActivityStats that = (ActivityStats) o;
        return ccTeacher == that.ccTeacher &&
                dmHost == that.dmHost &&
                firesideHost == that.firesideHost &&
                jygAnimator == that.jygAnimator &&
                scTutor == that.scTutor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ccTeacher, dmHost, firesideHost, jygAnimator, scTutor);
    }

    @Override
    public String toString() {
        return "ActivityStats{" +
                "ccTeacher=" + ccTeacher +
                ", dmHost=" + dmHost +
                ", firesideHost=" + firesideHost +
                ", jygAnimator=" + jygAnimator +
                ", scTutor=" + scTutor +
                '}';
    }
}
