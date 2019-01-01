package com.tiansen.ordermanager.mybatis.service.impl;

import com.tiansen.ordermanager.mybatis.entity.Purchase;
import com.tiansen.ordermanager.mybatis.mapper.PurchaseMapper;
import com.tiansen.ordermanager.mybatis.service.IPurchaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 采购 服务实现类
 * </p>
 *
 * @author rylai
 * @since 2019-01-01
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements IPurchaseService {

}
