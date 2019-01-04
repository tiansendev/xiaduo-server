package com.tiansen.ordermanager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.ProductDefinition;
import com.tiansen.ordermanager.mybatis.fill.CreateFieldFill;
import com.tiansen.ordermanager.common.util.SortProcessor;
import com.tiansen.ordermanager.mybatis.service.IProductDefinitionService;
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
@Api(tags = {"产品定义API接口"})
@RequestMapping("/product_definition")
public class ProductDefinitionController {

    @Autowired
    private IProductDefinitionService iProductDefinitionService;

    @ApiOperation(value = "添加定义")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResult add(@RequestBody ProductDefinition productDefinition) throws Exception {
        if (productDefinition == null || StringUtils.isAnyBlank(productDefinition.getProdDefName(),productDefinition.getProdDefSpec()))
            throw  new ParameterIllegalException("参数不合法");
        CreateFieldFill.fill(productDefinition);
        iProductDefinitionService.save(productDefinition);
        return ServiceResult.success();
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServiceResult delComb(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        iProductDefinitionService.removeById(id);
        return ServiceResult.success();
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delbatch", method = RequestMethod.DELETE)
    public ServiceResult delBatch(@RequestBody List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        iProductDefinitionService.removeByIds(ids);
        return ServiceResult.success();
    }

    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServiceResult findById(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        return ServiceResult.success(iProductDefinitionService.getById(id));
    }

    @ApiOperation(value = "批量查询", notes = "批量查询")
    @RequestMapping(value = "/byids", method = RequestMethod.GET)
    public ServiceResult findByIds(@RequestParam(value = "ids")  List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        return ServiceResult.success(iProductDefinitionService.listByIds(ids));
    }

    @ApiOperation(value = "条件查询，分页", notes = "条件查询")
    @RequestMapping(value = "/bypage", method = RequestMethod.GET)
    public ServiceResult findByCondByPage(
            @RequestParam(value = "name", required = false) String name,
            @PageableDefault(value = 10, sort = {"update_date"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws Exception {

        QueryWrapper<ProductDefinition> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.eq(ProductDefinition.PROD_DEF_NAME, name);
        }
        SortProcessor.process(queryWrapper, pageable);
        Page<ProductDefinition> page = new Page<>(pageable.getPageSize(), pageable.getPageNumber());
        return ServiceResult.success(iProductDefinitionService.page(page, queryWrapper));
    }

    @ApiOperation(value = "条件查询")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ServiceResult findByCond(
            @RequestParam(value = "name", required = false) String name,
            @SortDefault(sort = {"update_date"}, direction = Sort.Direction.DESC) Sort sort
    ) throws Exception {
        QueryWrapper<ProductDefinition> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.eq(ProductDefinition.PROD_DEF_NAME, name);
        }
        SortProcessor.process(queryWrapper, sort);
        return ServiceResult.success(iProductDefinitionService.list(queryWrapper));
    }

    @ApiOperation(value = "查看详情", notes = "条件查询")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ServiceResult update(@RequestBody ProductDefinition productDefinition) throws Exception {
        if (productDefinition == null || productDefinition.getId() == null)
            throw new ParameterIllegalException();
        iProductDefinitionService.updateById(productDefinition);
        return ServiceResult.success();
    }
}
