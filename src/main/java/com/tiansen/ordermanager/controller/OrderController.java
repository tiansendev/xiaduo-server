package com.tiansen.ordermanager.controller;


import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.mybatis.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rylai
 * @since 2018-12-30
 */
@Api(value = "订单API接口",tags = {"订单API接口"})
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    @ApiOperation(value = "添加仓库", notes = "添加仓库")
    @ApiImplicitParam(name = "assetsstore", value = "仓库实体，一级仓库无需SuperId，仓库名称，仓库位置，备注需要前端填写，其它服务器自动生成,需管理员权限", required = true, dataType = "Assetsstore", paramType = "body")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResult addAssetsStore(@RequestParam("file") MultipartFile file) throws Exception {
        iOrderService.addModel(file);
        return ServiceResult.success();
    }
}