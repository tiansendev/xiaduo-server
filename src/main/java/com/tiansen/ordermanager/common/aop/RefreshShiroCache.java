package com.tiansen.ordermanager.common.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RefreshShiroCache {
    /**
     * 处理参数索引,用于判断是user还是role, 默认-1：不判断方法参数，直接清除缓存
     * @return
     */
    int paramIndex() default -1;
}
