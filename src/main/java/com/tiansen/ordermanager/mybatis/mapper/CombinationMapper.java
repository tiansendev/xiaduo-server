package com.tiansen.ordermanager.mybatis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.mybatis.entity.Combination;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiansen.ordermanager.mybatis.entity.join.combination.CombinationDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
public interface CombinationMapper extends BaseMapper<Combination> {


    CombinationDetail findDetailById(Integer id);

    Page<CombinationDetail> findDetailByCondMap(Page<Combination> page, @Param("condMap") Map<String, Object> condMap);
}
