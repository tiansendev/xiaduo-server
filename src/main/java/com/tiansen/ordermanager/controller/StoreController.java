package com.tiansen.ordermanager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.common.util.RylaiRandom;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.Store;
import com.tiansen.ordermanager.mybatis.fill.CreateFieldFill;
import com.tiansen.ordermanager.mybatis.fill.DefaultOrderFill;
import com.tiansen.ordermanager.mybatis.fill.UpdateFieldFill;
import com.tiansen.ordermanager.mybatis.service.IStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
@Api(tags = "仓库接口")
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private IStoreService iStoreService;

    @ApiOperation(value = "添加")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResult add(@RequestBody Store store) throws Exception {
        if (store == null || StringUtils.isBlank(store.getStoName()))
            throw  new ParameterIllegalException("参数不合法");
        CreateFieldFill.fill(store);
        iStoreService.save(store);
        return ServiceResult.success();
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServiceResult delete(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        iStoreService.removeById(id);
        return ServiceResult.success();
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delbatch", method = RequestMethod.DELETE)
    public ServiceResult delBatch(@RequestBody List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        iStoreService.removeByIds(ids);
        return ServiceResult.success();
    }

    @ApiOperation(value = "根据仓库名称查询")
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ServiceResult findById(@PathVariable("name") String name) throws Exception {
        return ServiceResult.success(iStoreService.getOne(
                new QueryWrapper<Store>()
                .eq(Store.STO_NAME, name)
        ));
    }

    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServiceResult findById(@PathVariable("id") Integer id) throws Exception {
        return ServiceResult.success(iStoreService.getById(id));
    }

    @ApiOperation(value = "批量查询", notes = "批量查询")
    @RequestMapping(value = "/byids", method = RequestMethod.GET)
    public ServiceResult findByIds(@RequestParam(value = "ids")  List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        return ServiceResult.success(iStoreService.listByIds(ids));
    }

    @ApiOperation(value = "查询，分页", notes = "条件查询")
    @RequestMapping(value = "/bypage", method = RequestMethod.GET)
    public ServiceResult findByCondByPage(@PageableDefault(value = 10) Pageable pageable) throws Exception {
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        DefaultOrderFill.fillOrderDefault(queryWrapper);
        Page<Store> page = new Page<>(pageable.getPageSize(), pageable.getPageNumber());
        return ServiceResult.success(iStoreService.page(page, queryWrapper));
    }

    @ApiOperation(value = "查询所有")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ServiceResult findByCond() throws Exception {
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        DefaultOrderFill.fillOrderDefault(queryWrapper);
        return ServiceResult.success(iStoreService.list(queryWrapper));
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ServiceResult update(@RequestBody Store store) throws Exception {
        if (store == null || store.getId() == null)
            throw new ParameterIllegalException();
        UpdateFieldFill.fill(store);
        iStoreService.updateById(store);
        return ServiceResult.success();
    }
}
