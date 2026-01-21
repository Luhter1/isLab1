package org.itmo.isLab1.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.stats.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Aspect
@Component
public class CacheStatisticsAspect {

    private static final Logger log = LoggerFactory.getLogger(CacheStatisticsAspect.class);

    private final EmbeddedCacheManager embeddedCacheManager;

    @Value("${cache.statistics.enabled:false}")
    private boolean enabled;

    @Value("${cache.statistics.log-level:DEBUG}")
    private String logLevel;

    @Autowired
    public CacheStatisticsAspect(EmbeddedCacheManager embeddedCacheManager) {
        this.embeddedCacheManager = embeddedCacheManager;
    }

    @Around("execution(* org.itmo.isLab1.*.service..*(..))")
    public Object logCacheStatistics(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!enabled) {
            return joinPoint.proceed();
        }

        CacheCounters before = collectCounters();
        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            CacheCounters after = collectCounters();
            long hits = after.hits - before.hits;
            long misses = after.misses - before.misses;

            String method = joinPoint.getSignature().toShortString();
            String msg = "L2 cache stats for " + method + ": hits=" + hits + ", misses=" + misses;

            logWithLevel(msg);
        }
        return result;
    }

    private CacheCounters collectCounters() {
        long hits = 0;
        long misses = 0;

        Collection<String> cacheNames = embeddedCacheManager.getCacheNames();
        for (String cacheName : cacheNames) {
            try {
                Cache<?, ?> cache = embeddedCacheManager.getCache(cacheName, false);
                if (cache == null) {
                    continue;
                }

                // Stats can be disabled by config; guard for safety.
                Stats stats = cache.getAdvancedCache().getStats();
                if (stats == null) {
                    continue;
                }
                hits += stats.getHits();
                misses += stats.getMisses();
            } catch (Exception ignored) {
                // Do not break business flow due to stats issues.
            }
        }

        return new CacheCounters(hits, misses);
    }

    private void logWithLevel(String msg) {
        String level = (logLevel == null ? "" : logLevel.trim()).toUpperCase();
        switch (level) {
            case "TRACE" -> log.trace(msg);
            case "DEBUG" -> log.debug(msg);
            case "INFO" -> log.info(msg);
            case "WARN" -> log.warn(msg);
            case "ERROR" -> log.error(msg);
            default -> log.debug(msg);
        }
    }

    private record CacheCounters(long hits, long misses) {}
}
