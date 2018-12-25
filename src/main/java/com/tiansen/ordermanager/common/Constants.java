package com.tiansen.ordermanager.common;

public class Constants {
    /**
     * Shiro session cache name
     */
    public static final String SHIRO_SESSION_CACHE_NAME = "sessionCache";

    /**
     * Shiro authorization cache name
     */
    public static final String SHIRO_AUTHORIZATION_CACHE_NAME = "authorizationCache";

    /**
     * session超时时间，默认24h
     */
    public static final int SHIRO_SESSION_TIMEOUT_MILL_SECONDS = 1000 * 60 * 60 * 24;
}
