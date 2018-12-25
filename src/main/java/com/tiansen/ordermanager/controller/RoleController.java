package com.tiansen.ordermanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.*;
import com.tiansen.ordermanager.mybatis.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@Api(value="角色接口")
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @ApiOperation(value="添加角色" ,notes="")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResult<Collection<SysMenu>> addRole(@RequestBody SysRoleReq sysRoleReq) {
        sysRoleService.addRole(sysRoleReq);
        return ServiceResult.success();
    }

    @ApiOperation(value="批量添加角色" ,notes="")
    @RequestMapping(value = "/addbatch", method = RequestMethod.POST)
    public ServiceResult<Collection<SysMenu>> addRole(@RequestBody List<SysRoleReq> sysRoleReqs) {
        sysRoleService.addBatchRoles(sysRoleReqs);
        return ServiceResult.success();
    }

    @ApiOperation(value="根据id获取角色" ,notes="")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServiceResult<SysRole> findById(@PathVariable("id") Integer id) {
        if (id == null) {
            throw new ParameterIllegalException("id不能为空");
        }
        return ServiceResult.success(sysRoleService.getById(id));
    }

    @ApiOperation(value="获取角色详情" ,notes="")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public ServiceResult<?> findDetails(@PathVariable("id") Integer id) {
        if (id == null) {
            throw new ParameterIllegalException("id不能为空");
        }
        return ServiceResult.success(sysRoleService.getDetails(id));
    }

    @ApiOperation(value="获取角色" ,notes="")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ServiceResult<?> find(
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "roleStatus", required = false) Integer roleStatus,
            @PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.ASC)Pageable pageable
    ) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(roleName)) {
            wrapper.eq(SysRole.ROLE_NAME, roleName);
        }

        if (roleStatus != null) {
            wrapper.eq(SysRole.ROLE_STATUS, roleStatus);
        }

        Page page = new Page(pageable.getPageNumber(), pageable.getPageSize());
        return ServiceResult.success(sysRoleService.page(page, wrapper));
    }

    @ApiOperation(value="获取角色,不分页" ,notes="")
    @RequestMapping(value = "/unpage", method = RequestMethod.GET)
    public ServiceResult<?> find(
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "roleStatus", required = false) Integer roleStatus
    ) {

        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(roleName)) {
            wrapper.eq(SysRole.ROLE_NAME, roleName);
        }

        if (roleStatus != null) {
            wrapper.eq(SysRole.ROLE_STATUS, roleStatus);
        }

        return ServiceResult.success(sysRoleService.list(wrapper));
    }

    @ApiOperation(value="修改权限信息" ,notes="")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ServiceResult<?> edit(@RequestBody SysRoleReq sysRoleReq) {
        sysRoleService.edit(sysRoleReq);
        return ServiceResult.success();
    }

    @ApiOperation(value="删除权限信息" ,notes="")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ServiceResult<?> delete(@PathVariable("id") Integer id) {
        if (id ==  null) {
            throw new ParameterIllegalException();
        }
        sysRoleService.deleteById(id);
        return ServiceResult.success();
    }

    @ApiOperation(value="删除权限信息" ,notes="")
    @RequestMapping(value = "/delbatch", method = RequestMethod.DELETE)
    public ServiceResult<?> deleteBatch(@RequestBody List<Integer> ids) {
        if (ids ==  null) {
            throw new ParameterIllegalException();
        }
        sysRoleService.deleteByIds(ids);
        return ServiceResult.success();
    }



}
