package com.tiansen.ordermanager.mybatis.service.impl;

import com.tiansen.ordermanager.mybatis.entity.Product;
import com.tiansen.ordermanager.mybatis.mapper.ProductMapper;
import com.tiansen.ordermanager.mybatis.service.IProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
