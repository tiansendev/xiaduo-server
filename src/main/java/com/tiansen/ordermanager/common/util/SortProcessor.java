package com.tiansen.ordermanager.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortProcessor {
    public static <T> void process(QueryWrapper<T> queryWrapper, Pageable pageable) {
        List<String> ascs = new ArrayList<>();
        List<String> descs = new ArrayList<>();
        handlePageable(pageable.getSort(), ascs, descs);
        if (ascs.size() != 0) {
            String[] arrAscs = new String[ascs.size()];
            queryWrapper.orderByAsc(arrAscs);
        }
        if (descs.size() != 0) {
            String[] arrDescs = new String[descs.size()];
            queryWrapper.orderByDesc(arrDescs);
        }
    }

    public static <T> void process(Page<T> page, Pageable pageable) {
        List<String> ascs = new ArrayList<>();
        List<String> descs = new ArrayList<>();

        handlePageable(pageable.getSort(), ascs, descs);

        if (ascs.size() != 0) {
            page.setAscs(ascs);
        }
        if (descs.size() != 0) {
            page.setDescs(descs);
        }
    }

    private static void handlePageable(Sort sort, List<String> ascs, List<String> descs) {
        try {
            // BEGIN-> id: ASC,name: DESC
            String sortStr = sort.toString().replaceAll(" ", "");
            // THEN-> id:ASC,name:DESC
            String[] split = sortStr.split(",");
            // THEN->["id:ASC", "name:DESC"]

            for (String st : split) {
                String[] sp = st.split(":");
                //THEN->["id", "ASC"]
                if ("ASC".equals(sp[1])) {
                    ascs.add(sp[0]);
                } else if ("DESC".equals(sp[1])) {
                    descs.add(sp[0]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static <T> void process(Page<T> page, Sort sort) {
        List<String> ascs = new ArrayList<>();
        List<String> descs = new ArrayList<>();
        handlePageable(sort, ascs, descs);
        if (ascs.size() != 0) {
            page.setAscs(ascs);
        }
        if (descs.size() != 0) {
            page.setDescs(descs);
        }
    }

    public static <T> void process(QueryWrapper<T> queryWapper, Sort sort) {
        List<String> ascs = new ArrayList<>();
        List<String> descs = new ArrayList<>();
        handlePageable(sort, ascs, descs);
        if (ascs.size() != 0) {
            String[] arrAscs = new String[ascs.size()];
            queryWapper.orderByAsc(arrAscs);
        }
        if (descs.size() != 0) {
            String[] arrDescs = new String[descs.size()];
            queryWapper.orderByDesc(arrDescs);
        }
    }
}
