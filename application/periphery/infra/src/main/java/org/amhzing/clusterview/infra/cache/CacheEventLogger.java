package org.amhzing.clusterview.infra.cache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEventLogger implements CacheEventListener<Object, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheEventLogger.class);

    @Override
    public void onEvent(final CacheEvent<?, ?> event) {
        //LOGGER.info("Event: {} Key: {} old value: {} new value: {}", event.getType(), event.getKey(), event.getOldValue(), event.getNewValue());
        LOGGER.debug("Cache {} with key: {}", event.getType(), event.getKey());
    }
}
