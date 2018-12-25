package com.tiansen.ordermanager.mybatis.service.impl;

import com.tiansen.ordermanager.mybatis.entity.Order;
import com.tiansen.ordermanager.mybatis.mapper.OrderMapper;
import com.tiansen.ordermanager.mybatis.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rylai
 * @since 2018-12-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
