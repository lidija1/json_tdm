package json_core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class TestDataCache {
    private static final Logger logger = LogManager.getLogger(TestDataCache.class);
    private static final Cache<String, Object> testDataCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .recordStats()
            .build();

    public static void cacheTestData(String key, Object data) {
        testDataCache.put(key, data);
        logger.debug("Cached test data with key: {}", key);
    }

    public static Object getTestData(String key) {
        Object data = testDataCache.getIfPresent(key);
        if (data == null) {
            logger.debug("Cache miss for key: {}", key);
        }
        return data;
    }

    public static void invalidateCache() {
        testDataCache.invalidateAll();
        logger.info("Test data cache invalidated");
    }

    public static String getCacheStats() {
        return testDataCache.stats().toString();
    }
}
