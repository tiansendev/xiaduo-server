package com.tiansen.ordermanager.mybatis.service;

import com.tiansen.ordermanager.exception.BusinessException;
import com.tiansen.ordermanager.mybatis.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rylai
 * @since 2018-12-30
 */
public interface IOrderService extends IService<Order> {

    @Transactional(rollbackFor = BusinessException.class)
    void addModel(MultipartFile excelFile) throws Exception;
}
