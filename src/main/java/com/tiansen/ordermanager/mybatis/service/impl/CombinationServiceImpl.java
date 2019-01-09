package com.tiansen.ordermanager.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.mybatis.entity.Combination;
import com.tiansen.ordermanager.mybatis.entity.ProductDefinition;
import com.tiansen.ordermanager.mybatis.entity.join.combination.CombinationDefInfo;
import com.tiansen.ordermanager.common.util.SortProcessor;
import com.tiansen.ordermanager.mybatis.mapper.CombinationMapper;
import com.tiansen.ordermanager.mybatis.mapper.ProductDefinitionMapper;
import com.tiansen.ordermanager.mybatis.service.ICombinationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
@Service
public class CombinationServiceImpl extends ServiceImpl<CombinationMapper, Combination> implements ICombinationService {

    @Autowired
    private CombinationMapper combinationMapper;

    @Autowired
    private ProductDefinitionMapper productDefinitionMapper;

    @Override
    public List<Combination> findByCond(String name, String prodName, Sort sort) {
        QueryWrapper<Combination> queryWapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWapper.eq(Combination.COMB_NAME, name);
        }
        if (StringUtils.isNotBlank(prodName)) {
            queryWapper.eq(ProductDefinition.PROD_DEF_NAME, prodName);
        }

        SortProcessor.process(queryWapper, sort);

        return list(queryWapper);
    }

    @Override
    public IPage<CombinationDefInfo> findByCondByPage(String name, String prodName, String patternName, Pageable pageable) {
        Map<String, Object> condMap = new HashMap<>();
        if (StringUtils.isNotBlank(name)) {
            condMap.put(Combination.COMB_NAME, name);
        }
        if (StringUtils.isNotBlank(prodName)) {
            condMap.put(ProductDefinition.PROD_DEF_NAME, prodName);
        }
        if (StringUtils.isNotBlank(patternName)) {
            condMap.put("pattern_name", patternName);
        }

        Page<Combination> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        SortProcessor.process(page, pageable);

        Page<CombinationDefInfo> detailByCondMap = combinationMapper.findDetailByCondMap(page,condMap);

        return detailByCondMap;
    }

    @Override
    public Combination findDetailById(Integer id) {
        CombinationDefInfo detail =  combinationMapper.findDetailById(id);
        return detail;
    }

}
