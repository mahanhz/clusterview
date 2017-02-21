package org.amhzing.clusterview.app.log;

import ch.qos.logback.core.spi.FilterReply;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeBasedDuplicateMessageFilterTest {

    @Test
    public void should_not_allow_repetitions() {
        final TimeBasedDuplicateMessageFilter dmf = new TimeBasedDuplicateMessageFilter();
        dmf.setAllowedRepetitions(0);
        dmf.start();

        assertThat(logMessage(dmf, "x")).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, "y")).isEqualTo(FilterReply.NEUTRAL);

        // repetition
        assertThat(logMessage(dmf, "x")).isEqualTo(FilterReply.DENY);
        assertThat(logMessage(dmf, "y")).isEqualTo(FilterReply.DENY);
    }

    @Test
    public void should_allow_one_repetition() {
        final TimeBasedDuplicateMessageFilter dmf = new TimeBasedDuplicateMessageFilter();
        dmf.setAllowedRepetitions(1);
        dmf.start();

        assertThat(logMessage(dmf, "x")).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, "y")).isEqualTo(FilterReply.NEUTRAL);

        // repetition 1
        assertThat(logMessage(dmf, "x")).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, "y")).isEqualTo(FilterReply.NEUTRAL);

        // repetition 2
        assertThat(logMessage(dmf, "x")).isEqualTo(FilterReply.DENY);
        assertThat(logMessage(dmf, "y")).isEqualTo(FilterReply.DENY);
    }

    @Test
    public void should_allow_null_repetitions() {
        final TimeBasedDuplicateMessageFilter dmf = new TimeBasedDuplicateMessageFilter();
        dmf.setAllowedRepetitions(0);
        dmf.start();

        assertThat(logMessage(dmf, null)).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, null)).isEqualTo(FilterReply.NEUTRAL);
    }

    @Test
    public void should_allow_repetitions_when_cache_is_too_small() {
        final TimeBasedDuplicateMessageFilter dmf = new TimeBasedDuplicateMessageFilter();
        dmf.setAllowedRepetitions(1);
        dmf.setCacheSize(1);
        dmf.start();

        assertThat(logMessage(dmf, "x")).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, "y")).isEqualTo(FilterReply.NEUTRAL);

        // repetition 1
        assertThat(logMessage(dmf, "x")).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, "y")).isEqualTo(FilterReply.NEUTRAL);

        // repetition 2
        assertThat(logMessage(dmf, "x")).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, "y")).isEqualTo(FilterReply.NEUTRAL);
    }

    private FilterReply logMessage(final TimeBasedDuplicateMessageFilter dmf, final String message) {
        return dmf.decide(null, null, null, message, null, null);
    }
}