package com.tiansen.ordermanager.mybatis.service.impl;

import com.tiansen.ordermanager.mybatis.entity.OrderCombination;
import com.tiansen.ordermanager.mybatis.mapper.OrderCombinationMapper;
import com.tiansen.ordermanager.mybatis.service.IOrderCombinationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单-组合中间表 服务实现类
 * </p>
 *
 * @author rylai
 * @since 2019-01-03
 */
@Service
public class OrderCombinationServiceImpl extends ServiceImpl<OrderCombinationMapper, OrderCombination> implements IOrderCombinationService {

}
