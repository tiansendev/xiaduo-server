package com.tiansen.ordermanager.mybatis.mapper;

import com.tiansen.ordermanager.mybatis.entity.Combination;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiansen.ordermanager.mybatis.entity.join.CombinationDetail;

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
}
