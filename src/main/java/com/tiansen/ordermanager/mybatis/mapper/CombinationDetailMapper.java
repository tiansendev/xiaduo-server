package com.tiansen.ordermanager.mybatis.mapper;

import com.tiansen.ordermanager.mybatis.entity.CombinationDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 组合详情 Mapper 接口
 * </p>
 *
 * @author rylai
 * @since 2019-01-05
 */
public interface CombinationDetailMapper extends BaseMapper<CombinationDetail> {

    void saveBatch(List<CombinationDetail> details);
}
