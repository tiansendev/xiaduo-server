package com.tiansen.ordermanager.controller;


import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.mybatis.entity.Order;
import com.tiansen.ordermanager.mybatis.entity.join.order.OrderReq;
import com.tiansen.ordermanager.mybatis.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rylai
 * @since 2018-12-30
 */
@Api(tags = {"订单API接口"})
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    @ApiOperation(value = "添加", notes = "添加仓库")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResult addAssetsStore(@RequestBody OrderReq orderReq) throws Exception {
//        iOrderService.addModel(file);
        iOrderService.addOrder(orderReq);
        return ServiceResult.success();
    }
}