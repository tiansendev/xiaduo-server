package com.tiansen.ordermanager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.Supplier;
import com.tiansen.ordermanager.mybatis.fill.CreateFieldFill;
import com.tiansen.ordermanager.common.util.SortProcessor;
import com.tiansen.ordermanager.mybatis.fill.UpdateFieldFill;
import com.tiansen.ordermanager.mybatis.service.ISupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
@RestController
@Api(tags = "供应商接口")
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private ISupplierService iSupplierService;

    @ApiOperation(value = "添加")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResult add(@RequestBody Supplier supplier) throws Exception {
        if (supplier == null || StringUtils.isBlank(supplier.getSupName()))
            throw  new ParameterIllegalException("参数不合法");
        CreateFieldFill.fill(supplier);
        iSupplierService.save(supplier);
        return ServiceResult.success();
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServiceResult delete(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        iSupplierService.removeById(id);
        return ServiceResult.success();
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delbatch", method = RequestMethod.DELETE)
    public ServiceResult delBatch(@RequestBody List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        iSupplierService.removeByIds(ids);
        return ServiceResult.success();
    }

    @ApiOperation(value = "根据供应商名称查询")
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ServiceResult findByName(@PathVariable("name") String name) throws Exception {
        return ServiceResult.success(iSupplierService.getOne(
                new QueryWrapper<Supplier>()
                        .eq(Supplier.SUP_NAME, name)
        ));
    }

    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServiceResult findById(@PathVariable("id") Integer id) throws Exception {
        return ServiceResult.success(iSupplierService.getById(id));
    }

    @ApiOperation(value = "批量查询", notes = "批量查询")
    @RequestMapping(value = "/byids", method = RequestMethod.GET)
    public ServiceResult findByIds(@RequestParam(value = "ids")  List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        return ServiceResult.success(iSupplierService.listByIds(ids));
    }

    @ApiOperation(value = "查询，分页", notes = "条件查询")
    @RequestMapping(value = "/bypage", method = RequestMethod.GET)
    public ServiceResult findByCondByPage(
            @PageableDefault(value = 10, sort = {"update_date"}, direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        QueryWrapper<Supplier> queryWrapper = new QueryWrapper<>();
        SortProcessor.process(queryWrapper, pageable);
        Page<Supplier> page = new Page<>(pageable.getPageSize(), pageable.getPageNumber());
        return ServiceResult.success(iSupplierService.page(page, queryWrapper));
    }

    @ApiOperation(value = "查询所有")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ServiceResult findByCond(@SortDefault(sort = {"update_date"}, direction = Sort.Direction.DESC) Sort sort) throws Exception {
        QueryWrapper<Supplier> queryWrapper = new QueryWrapper<>();
        SortProcessor.process(queryWrapper, sort);
        return ServiceResult.success(iSupplierService.list(queryWrapper));
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ServiceResult update(@RequestBody Supplier supplier) throws Exception {
        if (supplier == null || supplier.getId() == null)
            throw new ParameterIllegalException();
        UpdateFieldFill.fill(supplier);
        iSupplierService.updateById(supplier);
        return ServiceResult.success();
    }
}
