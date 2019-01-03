package com.tiansen.ordermanager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.Express;
import com.tiansen.ordermanager.mybatis.fill.CreateFieldFill;
import com.tiansen.ordermanager.mybatis.fill.DefaultOrderFill;
import com.tiansen.ordermanager.mybatis.fill.UpdateFieldFill;
import com.tiansen.ordermanager.mybatis.service.IExpressService;
import com.tiansen.ordermanager.mybatis.service.IExpressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Api(tags = {"消费品定义API接口"})
@RequestMapping("/express")
public class ExpressController {

    @Autowired
    private IExpressService iExpressService;

    @ApiOperation(value = "添加")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResult add(@RequestBody Express express) throws Exception {
        if (express == null || StringUtils.isAnyBlank(express.getExpAddress(), express.getExpOrderCode(), express.getExpCompany(),
                express.getExpMobile(), express.getExpRecipient()))
            throw  new ParameterIllegalException("参数不合法");
        CreateFieldFill.fill(express);
        iExpressService.save(express);
        return ServiceResult.success();
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServiceResult delComb(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        iExpressService.removeById(id);
        return ServiceResult.success();
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delbatch", method = RequestMethod.DELETE)
    public ServiceResult delBatch(@RequestBody List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        iExpressService.removeByIds(ids);
        return ServiceResult.success();
    }

    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServiceResult findById(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        return ServiceResult.success(iExpressService.getById(id));
    }

    @ApiOperation(value = "批量查询", notes = "批量查询")
    @RequestMapping(value = "/byids", method = RequestMethod.GET)
    public ServiceResult findByIds(@RequestParam(value = "ids")  List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        return ServiceResult.success(iExpressService.listByIds(ids));
    }

    @ApiOperation(value = "根据单号查询")
    @RequestMapping(value = "/bycode/{code}", method = RequestMethod.GET)
    public ServiceResult findByCode(@RequestParam(value = "code") String code) throws Exception {
        if (StringUtils.isBlank(code))
            throw new ParameterIllegalException();
        QueryWrapper<Express> wrapper = new QueryWrapper();
        wrapper.eq(Express.EXP_ORDER_CODE, code);
        return ServiceResult.success(iExpressService.getOne(wrapper));
    }

    @ApiOperation(value = "条件查询，分页", notes = "条件查询")
    @RequestMapping(value = "/bypage", method = RequestMethod.GET)
    public ServiceResult findByCondByPage(
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "recipient", required = false) String recipient,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "company", required = false) String company,
            @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) throws Exception {

        QueryWrapper<Express> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(address)) {
            queryWrapper.eq(Express.EXP_ADDRESS, address);
        }
        if (StringUtils.isNotBlank(recipient)) {
            queryWrapper.eq(Express.EXP_RECIPIENT, recipient);
        }
        if (StringUtils.isNotBlank(mobile)) {
            queryWrapper.eq(Express.EXP_MOBILE, mobile);
        }
        if (StringUtils.isNotBlank(company)) {
            queryWrapper.eq(Express.EXP_COMPANY, company);
        }
        DefaultOrderFill.fillOrderDefault(queryWrapper);
        Page<Express> page = new Page<>(pageable.getPageSize(), pageable.getPageNumber());
        return ServiceResult.success(iExpressService.page(page, queryWrapper));
    }

    @ApiOperation(value = "条件查询")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ServiceResult findByCond(
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "recipient", required = false) String recipient,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "company", required = false) String company

    ) throws Exception {
        QueryWrapper<Express> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(address)) {
            queryWrapper.eq(Express.EXP_ADDRESS, address);
        }
        if (StringUtils.isNotBlank(recipient)) {
            queryWrapper.eq(Express.EXP_RECIPIENT, recipient);
        }
        if (StringUtils.isNotBlank(mobile)) {
            queryWrapper.eq(Express.EXP_MOBILE, mobile);
        }
        if (StringUtils.isNotBlank(company)) {
            queryWrapper.eq(Express.EXP_COMPANY, company);
        }
        DefaultOrderFill.fillOrderDefault(queryWrapper);
        return ServiceResult.success(iExpressService.list(queryWrapper));
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ServiceResult update(@RequestBody Express express) throws Exception {
        if (express == null || express.getId() == null)
            throw new ParameterIllegalException();
        UpdateFieldFill.fill(express);
        iExpressService.updateById(express);
        return ServiceResult.success();
    }
}
