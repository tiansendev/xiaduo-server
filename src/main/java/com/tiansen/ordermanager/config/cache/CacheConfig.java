package com.tiansen.ordermanager.config.cache;

import net.sf.ehcache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Autowired
    private EhCacheManager railwayCacheManager;

    @Bean(name = "railwayCache")
    public Cache cache() {
        return railwayCacheManager.getCacheManager().getCache("railwayCache");
    }
}
