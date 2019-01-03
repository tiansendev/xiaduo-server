package com.tiansen.ordermanager.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiansen.ordermanager.common.aop.RefreshShiroCache;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.*;
import com.tiansen.ordermanager.mybatis.mapper.SysMenuMapper;
import com.tiansen.ordermanager.mybatis.mapper.SysRoleAccessMapper;
import com.tiansen.ordermanager.mybatis.mapper.SysRoleMapper;
import com.tiansen.ordermanager.mybatis.mapper.SysUserRoleMapper;
import com.tiansen.ordermanager.mybatis.service.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author rylai
 * @since 2018-11-16
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleAccessMapper sysRoleAccessMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(SysRoleReq sysRoleReq) {
        if (sysRoleReq == null) {
            throw new ParameterIllegalException();
        }
//        SysRole sysRole = sysRoleReq.getSysRole();
        if (sysRoleReq == null ||
                StringUtils.isAnyBlank(sysRoleReq.getRoleName())) {
            throw new ParameterIllegalException();
        }

        LocalDateTime currDate = LocalDateTime.now();
        // 保存角色信息
        sysRoleReq.setCreateDate(currDate);
        save(sysRoleReq);

        // 保存角色权限信息
        if (sysRoleReq.getAccessIds() != null && sysRoleReq.getAccessIds().size() > 0){
            List<SysRoleAccess> sysRoleAccesses = new ArrayList<>();
            for (Integer accid : sysRoleReq.getAccessIds()) {
                SysRoleAccess sysRoleAccess = new SysRoleAccess();
                sysRoleAccess.setAccessId(accid)
                        .setRoleId(sysRoleReq.getId())
                        .setCreateDate(currDate);
                sysRoleAccesses.add(sysRoleAccess);
            }
            if (sysRoleAccesses.size() > 0)
                sysRoleAccessMapper.insertBatch(sysRoleAccesses);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatchRoles(List<SysRoleReq> sysRoleReqs) {
        if (sysRoleReqs == null || sysRoleReqs.size() == 0) {
            throw new ParameterIllegalException();
        }

        LocalDateTime currDate = LocalDateTime.now();

        List<SysRole> sysRoles = new ArrayList<>();
        List<SysRoleAccess> sysRoleAccesses = new ArrayList<>();
        for (SysRoleReq sysRoleReq : sysRoleReqs) {
            // 角色信息
            if (sysRoleReq == null
                    ||StringUtils.isBlank(sysRoleReq.getRoleName()))
                continue;
            sysRoleReq.setCreateDate(currDate);
            sysRoles.add(sysRoleReq);
        }

        // 保存角色信息
        saveBatch(sysRoles);


        // 权限信息
        for (SysRoleReq sysRoleReq : sysRoleReqs) {
//            SysRole sysRole = sysRoleReq.getSysRole();
            if (sysRoleReq.getAccessIds() != null && sysRoleReq.getAccessIds().size() > 0) {
                for (Integer id : sysRoleReq.getAccessIds()) {
                    SysRoleAccess sysRoleAccess = new SysRoleAccess();
                    sysRoleAccess.setAccessId(id)
                            .setRoleId(sysRoleReq.getId())
                            .setCreateDate(currDate);
                    sysRoleAccesses.add(sysRoleAccess);
                }
            }
        }

        // 保存权限信息
        if (sysRoleAccesses.size() > 0)
            sysRoleAccessMapper.insertBatch(sysRoleAccesses);

    }

    @Override
    @RefreshShiroCache
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        removeById(id);

        // 删除role_access表中相关数据
        sysRoleAccessMapper.delete(
                new QueryWrapper<SysRoleAccess>()
                        .eq(SysRoleAccess.ROLE_ID, id)
        );

        // 删除user_role表中相关数据
        sysUserRoleMapper.delete(
                new QueryWrapper<SysUserRole>()
                        .eq(SysUserRole.ROLE_ID, id)
        );
    }

    @Override
    @RefreshShiroCache
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Integer> ids) {
        removeByIds(ids);

        // 删除role_access表中相关数据
        sysRoleAccessMapper.delete(
                new QueryWrapper<SysRoleAccess>()
                        .in(SysRoleAccess.ROLE_ID, ids)
        );

        // 删除user_role表中相关数据
        sysUserRoleMapper.delete(
                new QueryWrapper<SysUserRole>()
                        .eq(SysUserRole.ROLE_ID, ids)
        );
    }

    @Override
    public SingleRoleAccessesInfo getDetails(Integer id) {
        if (id == null) {
            throw new ParameterIllegalException();
        }
        // 获取sysRole
        SingleRoleAccessesInfo singleRoleAccessesInfo = sysRoleAccessMapper.findSysRoleAccessInfosByRoleId(id);
        if (singleRoleAccessesInfo ==  null)
            return null;

        // 根据roleid获取所有menus
        List<Integer> menuIds = new ArrayList<>();
        for (Object menuId : singleRoleAccessesInfo.getMenuIds()) {
            menuIds.add((Integer) menuId);
        }
        List<SysMenu> menus = sysMenuMapper.selectBatchIds(menuIds);
        singleRoleAccessesInfo.setMenus(menus);

        return singleRoleAccessesInfo;
    }

    @Override
    @RefreshShiroCache(paramIndex = 0)
    @Transactional(rollbackFor = Exception.class)
    public void edit(SysRoleReq sysRole) {

        if (sysRole == null || sysRole.getId() == null) {
            throw new ParameterIllegalException();
        }

        // 更新role
//        updateById(sysRole);
        sysRoleMapper.updateRoleById(sysRole);

        // 修改角色权限信息
        if (sysRole.getAccessIds() != null && sysRole.getAccessIds().size() > 0){
//            // TODO: 18-11-28 不可以编辑admin
//            if ("admin".equals(sysRole.getRoleName())) {
//                throw new ParameterIllegalException("admin为超级管理员，权限不可编辑");
//            }
            // 修改前的ids
            List<Integer> orignIds = new ArrayList<>();
            List<SysRoleAccess> orignSysRoleAccesses
                    = sysRoleAccessMapper.selectList(
                    new QueryWrapper<SysRoleAccess>().eq(SysRoleAccess.ROLE_ID, sysRole.getId()));
            for (SysRoleAccess roleAccess : orignSysRoleAccesses) {
                orignIds.add(roleAccess.getAccessId());
            }
            // copy 当前权限数据
            List<Integer> removeIds = new ArrayList<>(orignIds);

            // 修改后的ids
            List<Integer> addIds = sysRole.getAccessIds();

            // need remove
            removeIds.removeAll(addIds);

            // need add
            addIds.removeAll(orignIds);

            // 添加
            List<SysRoleAccess> addedRoleAccess = new ArrayList<>();
            for (Integer accid : addIds) {
                SysRoleAccess sysRoleAccess = new SysRoleAccess();
                sysRoleAccess.setAccessId(accid)
                        .setRoleId(sysRole.getId())
                        .setCreateDate(LocalDateTime.now());
                addedRoleAccess.add(sysRoleAccess);
            }

            if (addedRoleAccess.size() > 0)
                sysRoleAccessMapper.insertBatch(addedRoleAccess);

            // 删除
            if (removeIds.size() > 0)
                sysRoleAccessMapper.delete(new QueryWrapper<SysRoleAccess>().in(SysRoleAccess.ACCESS_ID, removeIds));
        }
    }

}
