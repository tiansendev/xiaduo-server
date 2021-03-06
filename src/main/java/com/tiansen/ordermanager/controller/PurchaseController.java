package com.tiansen.ordermanager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.Purchase;
import com.tiansen.ordermanager.mybatis.entity.join.purchase.PurchaseReq;
import com.tiansen.ordermanager.common.util.SortProcessor;
import com.tiansen.ordermanager.mybatis.fill.UpdateFieldFill;
import com.tiansen.ordermanager.mybatis.service.IPurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 采购 前端控制器
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
@RestController
@Api(tags = "采购接口")
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private IPurchaseService iPurchaseService;

    @ApiOperation(value = "添加")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResult add(@RequestBody PurchaseReq purchase) throws Exception {
        iPurchaseService.addPurchase(purchase);
        return ServiceResult.success();
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServiceResult delete(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        iPurchaseService.removeById(id);
        return ServiceResult.success();
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delbatch", method = RequestMethod.DELETE)
    public ServiceResult delBatch(@RequestBody List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        iPurchaseService.removeByIds(ids);
        return ServiceResult.success();
    }

    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public ServiceResult findById(@PathVariable("id") Integer id) throws Exception {
        return ServiceResult.success(iPurchaseService.getDetailById(id));
    }

    @ApiOperation(value = "根据code查询", notes = "根据id查询")
    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    public ServiceResult findByCode(@PathVariable("code") String code) throws Exception {
        return ServiceResult.success(iPurchaseService.getOne(
                new QueryWrapper<Purchase>()
                .eq(Purchase.PUR_CODE, code)
        ));
    }

    @ApiOperation(value = "批量查询", notes = "批量查询")
    @RequestMapping(value = "/byids", method = RequestMethod.GET)
    public ServiceResult findByIds(@RequestParam(value = "ids")  List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        return ServiceResult.success(iPurchaseService.listByIds(ids));
    }

    @ApiOperation(value = "查询，分页", notes = "条件查询")
    @RequestMapping(value = "/bypage", method = RequestMethod.GET)
    public ServiceResult findByCondByPage(
            @PageableDefault(value = 10, sort = {"update_date"}, direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        QueryWrapper<Purchase> queryWrapper = new QueryWrapper<>();
        SortProcessor.process(queryWrapper, pageable);
        Page<Purchase> page = new Page<>(pageable.getPageSize(), pageable.getPageNumber());
        return ServiceResult.success(iPurchaseService.page(page, queryWrapper));
    }

    @ApiOperation(value = "查询所有")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ServiceResult findByCond(@SortDefault(sort = {"update_date"}, direction = Sort.Direction.DESC) Sort sort) throws Exception {
        QueryWrapper<Purchase> queryWrapper = new QueryWrapper<>();
        SortProcessor.process(queryWrapper, sort);
        return ServiceResult.success(iPurchaseService.list(queryWrapper));
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ServiceResult update(@RequestBody Purchase purchase) throws Exception {
        if (purchase == null || purchase.getId() == null)
            throw new ParameterIllegalException();
        UpdateFieldFill.fill(purchase);
        iPurchaseService.updateById(purchase);
        return ServiceResult.success();
    }
}
