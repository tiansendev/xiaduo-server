package com.tiansen.ordermanager.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

public class ShiroCacheOpsUtil {



    /**
     * 清空所有缓存
     * @param cacheName
     */
    public static void clear(String cacheName) {
        getCache(cacheName).clear();
    }

    /**
     * 移除指定缓存
     * @param chacheName
     * @param key
     */
    public static void remove(String chacheName, Object key) {
        getCache(chacheName).remove(key);

    }

    private static Cache<Object, Object> getCache(String cacheName) {
        DefaultWebSecurityManager defaultSecurityManager
                = ((DefaultWebSecurityManager) SecurityUtils.getSecurityManager());

        Cache<Object, Object> cache
                = defaultSecurityManager.getCacheManager().getCache(cacheName);
        return cache;
    }
}
