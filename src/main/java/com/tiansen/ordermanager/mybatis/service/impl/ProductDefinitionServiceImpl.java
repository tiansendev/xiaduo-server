package com.tiansen.ordermanager.mybatis.service.impl;

import com.tiansen.ordermanager.mybatis.entity.ProductDefinition;
import com.tiansen.ordermanager.mybatis.mapper.ProductDefinitionMapper;
import com.tiansen.ordermanager.mybatis.service.IProductDefinitionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
@Service
public class ProductDefinitionServiceImpl extends ServiceImpl<ProductDefinitionMapper, ProductDefinition> implements IProductDefinitionService {

}
