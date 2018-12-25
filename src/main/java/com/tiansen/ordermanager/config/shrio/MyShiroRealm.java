package com.tiansen.ordermanager.config.shrio;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiansen.ordermanager.common.code.ServiceResultCode;
import com.tiansen.ordermanager.exception.BusinessException;
import com.tiansen.ordermanager.mybatis.entity.SysRole;
import com.tiansen.ordermanager.mybatis.entity.SysRoleAccessInfo;
import com.tiansen.ordermanager.mybatis.entity.SysUser;
import com.tiansen.ordermanager.mybatis.entity.UserRoleInfo;
import com.tiansen.ordermanager.mybatis.mapper.SysRoleAccessMapper;
import com.tiansen.ordermanager.mybatis.mapper.SysUserMapper;
import com.tiansen.ordermanager.mybatis.mapper.SysUserRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by pangkunkun on 2017/11/15.
 */
@Configuration
public class MyShiroRealm extends AuthorizingRealm{
    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleAccessMapper sysRoleAccessMapper;

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        SysUser user = (SysUser) principals.getPrimaryPrincipal();

        UserRoleInfo userRoleInfo = sysUserRoleMapper.findUserRolesByUserId(user.getId());
        if (userRoleInfo == null)
            throw new AuthenticationException("当前用户未分配角色信息");

        List<Integer> roleIds = new ArrayList<>();
        for (SysRole role : userRoleInfo.getSysRole()) {
            if (role == null || StringUtils.isBlank(role.getRoleName()))
                continue;
            simpleAuthorizationInfo.addRole(role.getRoleName());
            roleIds.add(role.getId());
        }

        List<SysRoleAccessInfo> sysRoleAccessInfos = sysRoleAccessMapper.findAllSysRoleAccessInfosByRoleIds(roleIds);

        for (SysRoleAccessInfo info : sysRoleAccessInfos) {
            if (info == null || info.getSysAccess() == null || info.getSysRole() == null
                    || StringUtils.isAnyBlank(info.getSysRole().getRoleName(), info.getSysAccess().getAccMethod()))
                continue;
            simpleAuthorizationInfo.addStringPermission(info.getSysAccess().getAccMethod()+"/"+info.getSysAccess().getAccRequrl());
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String username = auth.getPrincipal().toString();
        String password = String.valueOf((char[]) auth.getCredentials());
        // 解密获得account，用于和数据库进行对比
        if (StringUtils.isAnyBlank(username, password)) {
            throw new AuthenticationException("用户名或密码为空");
        }

        SysUser user = sysUserMapper.selectOne(
                new QueryWrapper<SysUser>()
                        .eq(SysUser.USER_NAME, username)
                        .eq(SysUser.USER_PASSWORD, password)
        );

        if (user == null) {
            throw new BusinessException(ServiceResultCode.USER_PASSWORD_ERR.getCode());
        }

        return new SimpleAuthenticationInfo(user, password, "myShiroRealm");
    }
}