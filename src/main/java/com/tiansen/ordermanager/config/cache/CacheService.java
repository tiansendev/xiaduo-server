package com.tiansen.ordermanager.config.cache;

public interface CacheService {
    <T> boolean set(String key, T value);
    <T> T get(String key, Class<T> tClass);
    void remove(final String... keys);
    boolean exists(final String key);
}
