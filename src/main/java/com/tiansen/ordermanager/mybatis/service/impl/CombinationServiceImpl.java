package com.tiansen.ordermanager.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.mybatis.entity.Combination;
import com.tiansen.ordermanager.mybatis.entity.ProductDefinition;
import com.tiansen.ordermanager.mybatis.entity.join.CombinationDetail;
import com.tiansen.ordermanager.mybatis.fill.DefaultOrderFill;
import com.tiansen.ordermanager.mybatis.mapper.CombinationMapper;
import com.tiansen.ordermanager.mybatis.mapper.ProductDefinitionMapper;
import com.tiansen.ordermanager.mybatis.service.ICombinationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<Combination> findByCond(String name, String prodName) {
        QueryWrapper<Combination> queryWapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWapper.eq(Combination.COMB_NAME, name);
        }
        if (StringUtils.isNotBlank(prodName)) {
            queryWapper.eq(ProductDefinition.PROD_DEF_NAME, prodName);
        }

        DefaultOrderFill.fillOrderDefault(queryWapper);

        return list(queryWapper);
    }

    @Override
    public IPage<Combination> findByCondByPage(String name, String prodName, Pageable pageable) {
//        Map<String, Object> condMap = new HashMap<>();
//        if (StringUtils.isNotBlank(name)) {
//            condMap.put(Combination.COMB_NAME, name);
//        }
//        if (StringUtils.isNotBlank(prodName)) {
//            condMap.put(ProductDefinition.PROD_DEF_NAME, prodName);
//        }
//
        QueryWrapper<Combination> queryWapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWapper.eq(Combination.COMB_NAME, name);
        }
        if (StringUtils.isNotBlank(prodName)) {
            queryWapper.eq(ProductDefinition.PROD_DEF_NAME, prodName);
        }
        DefaultOrderFill.fillOrderDefault(queryWapper);
        Page<Combination> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        return page(page, queryWapper);
    }

    @Override
    public Combination findDetailById(Integer id) {
        Combination combination =  combinationMapper.selectById(id);
        if (combination == null || combination.getProdDefIds() == null || combination.getProdDefIds().length==0)
            return combination;
        List<Integer> prodDefIds = new ArrayList<>();
        for (Object menuId : combination.getProdDefIds()) {
            prodDefIds.add((Integer) menuId);
        }
        CombinationDetail detail = new CombinationDetail();
        detail.setId(combination.getId())
                .setCreateDate(combination.getCreateDate())
                .setUpdateDate(combination.getUpdateDate())
                .setCreatorId(combination.getCreatorId())
                .setCombRemark(combination.getCombRemark())
                .setCombName(combination.getCombName());
        List<ProductDefinition> productDefinitions = productDefinitionMapper.selectBatchIds(prodDefIds);
        detail.setProductDefinitions(productDefinitions);
        return detail;
    }

}
