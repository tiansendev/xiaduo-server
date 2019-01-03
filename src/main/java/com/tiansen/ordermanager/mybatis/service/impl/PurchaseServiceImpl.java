package com.tiansen.ordermanager.mybatis.service.impl;

import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.Purchase;
import com.tiansen.ordermanager.mybatis.entity.join.PurchaseDetail;
import com.tiansen.ordermanager.mybatis.mapper.PurchaseMapper;
import com.tiansen.ordermanager.mybatis.service.IPurchaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 采购 服务实现类
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements IPurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public PurchaseDetail getDetailById(Integer id) {
        if (id == null)
            throw new ParameterIllegalException();
        return purchaseMapper.findDetailById(id);
    }
}
