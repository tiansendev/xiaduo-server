package com.tiansen.ordermanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.mybatis.entity.SysMenu;
import com.tiansen.ordermanager.mybatis.entity.SysRole;
import com.tiansen.ordermanager.mybatis.entity.SysUser;
import com.tiansen.ordermanager.mybatis.entity.UserRoleInfo;
import com.tiansen.ordermanager.mybatis.mapper.SysUserRoleMapper;
import com.tiansen.ordermanager.mybatis.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
@Api(value="菜单接口")
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @ApiOperation(value="获取当前用户被分配的菜单" ,notes="")
    @RequestMapping(value = "/owner", method = RequestMethod.GET)
    public ServiceResult<Collection<SysMenu>> findOwnerMenus() {
        Subject currUser = SecurityUtils.getSubject();
        SysUser user = (SysUser) currUser.getPrincipal();

        // 查询userrole中间表，获取所有角色相关menuids
        UserRoleInfo info = userRoleMapper.findUserRolesByUserId(user.getId());
        Set<Integer> ids = new HashSet<>();
        for (SysRole role : info.getSysRole()) {
            if (role.getMenuIds() == null || role.getMenuIds().length == 0)
                continue;
            for (Object menuId : role.getMenuIds()) {
                if (menuId == null)
                    continue;
                ids.add((Integer) menuId);
            }
        }
        return ServiceResult.success(sysMenuService.listByIds(ids));
    }

    @ApiOperation(value="获取所有菜单" ,notes="")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ServiceResult<Collection<SysMenu>> findAllMenus() {
        return ServiceResult.success(sysMenuService.list(new QueryWrapper<>()));
    }

}
