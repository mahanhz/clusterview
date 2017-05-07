package org.amhzing.clusterview.infra.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Marker;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.*;

public class TimeBasedDuplicateMessageFilter extends TurboFilter {

    private static final int MAX_KEY_LENGTH = 100;
    public static final int DEFAULT_CACHE_SIZE = 100;
    public static final int DEFAULT_ALLOWED_REPETITIONS = 5;
    public static final int DEFAULT_EXPIRE_AFTER_WRITE_SECONDS = 60;

    public int allowedRepetitions = DEFAULT_ALLOWED_REPETITIONS;
    public int cacheSize = DEFAULT_CACHE_SIZE;
    public int expireAfterWriteSeconds = DEFAULT_EXPIRE_AFTER_WRITE_SECONDS;

    private Cache<String, Integer> msgCache;

    @Override
    public void start() {

        msgCache = buildCache();

        super.start();
    }

    @Override
    public void stop() {
        msgCache.invalidateAll();
        msgCache = null;

        super.stop();
    }

    @Override
    public FilterReply decide(final Marker marker, final Logger logger, final Level level,
                              final String format, final Object[] params, final Throwable t) {
        int count = 0;

        if (isNotBlank(format)) {
            final String key = abbreviate(format + paramsAsString(params, logger), MAX_KEY_LENGTH);

            final Integer msgCount = msgCache.getIfPresent(key);

            if (msgCount != null) {
                count = msgCount + 1;
            }

            msgCache.put(key, count);
        }

        return (count <= allowedRepetitions) ? FilterReply.NEUTRAL : FilterReply.DENY;
    }

    public int getAllowedRepetitions() {
        return allowedRepetitions;
    }

    public void setAllowedRepetitions(final int allowedRepetitions) {
        this.allowedRepetitions = allowedRepetitions;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(final int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public int getExpireAfterWriteSeconds() {
        return expireAfterWriteSeconds;
    }

    public void setExpireAfterWriteSeconds(final int expireAfterWriteSeconds) {
        this.expireAfterWriteSeconds = expireAfterWriteSeconds;
    }

    private String paramsAsString(final Object[] params, final Logger logger) {
        if (params != null && startsWith(logger.getName(), "org.amhzing.clusterview")) {
            return Arrays.stream(params).map(Object::toString).collect(Collectors.joining("_"));
        }

        return "";
    }

    private Cache<String, Integer> buildCache() {
        return CacheBuilder.newBuilder()
                           .expireAfterWrite(expireAfterWriteSeconds, TimeUnit.SECONDS)
                           .initialCapacity(cacheSize)
                           .maximumSize(cacheSize)
                           .build();
    }
}
