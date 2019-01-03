package com.tiansen.ordermanager.mybatis.fill;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class DefaultOrderFill {
    public static <T> void fillOrderDefault(QueryWrapper<T> queryWrapper) {
        queryWrapper.orderByDesc("update_date");
    }
}
