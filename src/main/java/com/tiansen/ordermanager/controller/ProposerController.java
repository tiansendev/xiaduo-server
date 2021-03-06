package com.tiansen.ordermanager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.Proposer;
import com.tiansen.ordermanager.mybatis.entity.emun.PropPropertyEmun;
import com.tiansen.ordermanager.mybatis.fill.CreateFieldFill;
import com.tiansen.ordermanager.common.util.SortProcessor;
import com.tiansen.ordermanager.mybatis.fill.UpdateFieldFill;
import com.tiansen.ordermanager.mybatis.service.IProposerService;
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
@Api(tags = "申请人接口")
@RequestMapping("/proposer")
public class ProposerController {

    @Autowired
    private IProposerService iProposerService;

    @ApiOperation(value = "添加")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResult add(@RequestBody Proposer proposer) throws Exception {
        if (proposer == null || StringUtils.isAnyBlank(proposer.getPropContactMan()))
            throw  new ParameterIllegalException("参数不合法");
        CreateFieldFill.fill(proposer);
        // 不设置 就是个人
        if (proposer.getPropProperty() == null)
            proposer.setPropProperty(PropPropertyEmun.PROPERTY_PERSON.getIndex());
        iProposerService.save(proposer);
        return ServiceResult.success();
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServiceResult delComb(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        iProposerService.removeById(id);
        return ServiceResult.success();
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delbatch", method = RequestMethod.DELETE)
    public ServiceResult delBatch(@RequestBody List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        iProposerService.removeByIds(ids);
        return ServiceResult.success();
    }

    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServiceResult findById(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        return ServiceResult.success(iProposerService.getById(id));
    }

    @ApiOperation(value = "批量查询", notes = "批量查询")
    @RequestMapping(value = "/byids", method = RequestMethod.GET)
    public ServiceResult findByIds(@RequestParam(value = "ids")  List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        return ServiceResult.success(iProposerService.listByIds(ids));
    }

    @ApiOperation(value = "条件查询，分页", notes = "条件查询")
    @RequestMapping(value = "/bypage", method = RequestMethod.GET)
    public ServiceResult findByCondByPage(
            @RequestParam(value = "property", required = false) String property,
            @RequestParam(value = "propName", required = false) String propName,
            @RequestParam(value = "propContactMan", required = false) String propContactMan,
            @PageableDefault(value = 10, sort = {"update_date"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws Exception {

        QueryWrapper<Proposer> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(property)) {
            queryWrapper.eq(Proposer.PROP_PROPERTY, property);
        }
        if (StringUtils.isNotBlank(propName)) {
            queryWrapper.eq(Proposer.PROP_NAME, propName);
        }
        if (StringUtils.isNotBlank(propContactMan)) {
            queryWrapper.eq(Proposer.PROP_CONTACT_MAN, propContactMan);
        }
        SortProcessor.process(queryWrapper, pageable);
        Page<Proposer> page = new Page<>(pageable.getPageSize(), pageable.getPageNumber());
        return ServiceResult.success(iProposerService.page(page, queryWrapper));
    }

    @ApiOperation(value = "条件查询")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ServiceResult findByCond(
            @RequestParam(value = "property", required = false) String property,
            @RequestParam(value = "propName", required = false) String propName,
            @RequestParam(value = "propContactMan", required = false) String propContactMan,
            @SortDefault(sort = {"update_date"}, direction = Sort.Direction.DESC) Sort sort
    ) throws Exception {
        QueryWrapper<Proposer> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(property)) {
            queryWrapper.eq(Proposer.PROP_PROPERTY, property);
        }
        if (StringUtils.isNotBlank(propName)) {
            queryWrapper.eq(Proposer.PROP_NAME, propName);
        }
        if (StringUtils.isNotBlank(propContactMan)) {
            queryWrapper.eq(Proposer.PROP_CONTACT_MAN, propContactMan);
        }
        SortProcessor.process(queryWrapper, sort);
        return ServiceResult.success(iProposerService.list(queryWrapper));
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ServiceResult update(@RequestBody Proposer proposer) throws Exception {
        if (proposer == null || proposer.getId() == null)
            throw new ParameterIllegalException();
        UpdateFieldFill.fill(proposer);
        iProposerService.updateById(proposer);
        return ServiceResult.success();
    }
}
