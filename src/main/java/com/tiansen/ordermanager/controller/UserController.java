package com.tiansen.ordermanager.controller;

import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.mybatis.entity.SysUser;
import com.tiansen.ordermanager.mybatis.entity.SysUserReq;
import com.tiansen.ordermanager.mybatis.service.impl.SysUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Api(value="用户接口")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @ApiOperation(value="用户登录" ,notes="")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServiceResult<?> Login(@RequestBody SysUser user) {
        return ServiceResult.success(sysUserService.login(user));
    }

    @ApiOperation(value="查询所有，分页(管理员权限)" ,notes="")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ServiceResult<?> findByPage(
            @RequestParam(value = "userName", required = false) String username,
            @RequestParam(value = "userMobile", required = false) String userMobile,
            @RequestParam(value = "userCode", required = false) String userCode,
            @RequestParam(value = "userGender", required = false) Integer userGender,
            @PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.ASC) Pageable pageable) {
        return ServiceResult.success(sysUserService.findUsersByPage(username, userMobile, userCode, userGender, pageable));
    }

    @ApiOperation(value="查询所有,不分页(管理员权限)" ,notes="")
    @RequestMapping(value = "/unpage", method = RequestMethod.GET)
    public ServiceResult<?> findUnPage(
            @RequestParam(value = "userName", required = false) String username,
            @RequestParam(value = "userMobile", required = false) String userMobile,
            @RequestParam(value = "userCode", required = false) String userCode,
            @RequestParam(value = "userGender", required = false) Integer userGender
    ) {
        return ServiceResult.success(sysUserService.findAll(username, userMobile, userCode, userGender));
    }

    @ApiOperation(value="根据id查询详情" ,notes="")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ServiceResult<?> findById(@PathVariable("id") Integer id) {
        return ServiceResult.success(sysUserService.getDetailById(id));
    }

    @ApiOperation(value="批量添加(管理员权限)" ,notes="")
    @RequestMapping(value = "/addbatch", method = RequestMethod.POST)
    public ServiceResult<?> addUsersBatch(@RequestBody List<SysUserReq> sysUserList) {
        sysUserService.addUserBatch(sysUserList);
        return ServiceResult.success();
    }

    @ApiOperation(value="添加用户(管理员权限)" ,notes="")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ServiceResult<?> addUser(@RequestBody SysUserReq sysUser) {
        sysUserService.addUser(sysUser);
        return ServiceResult.success();
    }

    @ApiOperation(value="编辑用户" ,notes="")
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public ServiceResult<?> editUser(@RequestBody SysUserReq sysUserReq) {
        sysUserService.editUser(sysUserReq);
        return ServiceResult.success();
    }

    @ApiOperation(value="删除用户(管理员权限)" ,notes="")
    @RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
    public ServiceResult<?> deleteUser(@PathVariable("id") Integer id) {
        sysUserService.deleteUser(id);
        return ServiceResult.success();
    }

    @ApiOperation(value="批量删除(管理员权限)" ,notes="")
    @RequestMapping(value = "/delbatch", method = RequestMethod.DELETE)
    public ServiceResult<?> deleteUserBatch(@RequestBody List<Integer> ids) {
        sysUserService.deleteBatch(ids);
        return ServiceResult.success();
    }

    @ApiOperation(value="上传头像" ,notes="")
    @RequestMapping(value = "/avatar/{id}", method = RequestMethod.POST)
    public ServiceResult<?> uploadAvatar(@RequestParam("avatar") MultipartFile avatar) throws Exception {
        sysUserService.uploadAvatar(avatar);
        return ServiceResult.success();
    }

}
