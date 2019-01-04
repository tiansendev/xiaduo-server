package com.tiansen.ordermanager.mybatis.fill;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.sf.ehcache.util.ProductInfo;

public class DefaultOrderFill {
    public static <T> void fillOrderDefault(QueryWrapper<T> queryWrapper) {
        queryWrapper.orderByDesc("update_date");
    }
    public static <T> void fillOrderDefault(Page<T> page) {
        page.setDesc("update_date");
    }
}
