package com.tiansen.ordermanager.common.aop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiansen.ordermanager.common.Constants;
import com.tiansen.ordermanager.common.util.ShiroCacheOpsUtil;
import com.tiansen.ordermanager.mybatis.entity.SysRole;
import com.tiansen.ordermanager.mybatis.entity.SysRoleReq;
import com.tiansen.ordermanager.mybatis.entity.SysUser;
import com.tiansen.ordermanager.mybatis.entity.SysUserRole;
import com.tiansen.ordermanager.mybatis.mapper.SysUserRoleMapper;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Component
@Aspect
public class RefreshShiroCacheService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    public RefreshShiroCacheService(){
    }


    /**
     * 切点
     */
    @Pointcut("@annotation(com.tiansen.ordermanager.common.aop.RefreshShiroCache)")
    public void methodRefreshShiroChachePointcut() {

    }

    @Around("methodRefreshShiroChachePointcut()")
    public void refreshShiroCache(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        }

        Object[] args = joinPoint.getArgs();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RefreshShiroCache refreshShiroCache = method.getAnnotation(RefreshShiroCache.class);
        int index = refreshShiroCache.paramIndex();

        if (args == null || index < 0) {
            ShiroCacheOpsUtil.clear(Constants.SHIRO_AUTHORIZATION_CACHE_NAME);
            return;
        }

        Object arg = args[index];

        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (arg instanceof SysUser) {
            // 修改用户信息
            Integer id = ((SysUser) arg).getId();
            if (id == user.getId()) {
                // 只删除当前user权限缓存
                ShiroCacheOpsUtil.remove(
                        Constants.SHIRO_AUTHORIZATION_CACHE_NAME,
                        SecurityUtils.getSubject().getPrincipals()
                );
            } else {
                ShiroCacheOpsUtil.clear(Constants.SHIRO_AUTHORIZATION_CACHE_NAME);
            }
        } else if (arg instanceof SysRoleReq) {
            // 修改角色信息
            SysRole sysRole = (SysRole) arg;
            if (sysRole == null)
                return;

            Integer id = sysRole.getId();
            // 查询user_role表
            List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(
                    new QueryWrapper<SysUserRole>()
                            .eq(SysUserRole.ROLE_ID, id));

            if (sysUserRoles != null
                    && sysUserRoles.size() == 1
                    && sysUserRoles.get(0).getUserId() == user.getId()) {
                // 仅当角色对应的userid只有当前用户时，删除当前缓存
                ShiroCacheOpsUtil.remove(
                        Constants.SHIRO_AUTHORIZATION_CACHE_NAME,
                        SecurityUtils.getSubject().getPrincipals()
                );
            } else {
                ShiroCacheOpsUtil.clear(Constants.SHIRO_AUTHORIZATION_CACHE_NAME);
            }
        } else {
            ShiroCacheOpsUtil.clear(Constants.SHIRO_AUTHORIZATION_CACHE_NAME);
        }
    }
}
