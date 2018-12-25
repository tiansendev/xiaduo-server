package com.tiansen.ordermanager.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiansen.ordermanager.mybatis.entity.SingleRoleAccessesInfo;
import com.tiansen.ordermanager.mybatis.entity.SysRole;
import com.tiansen.ordermanager.mybatis.entity.SysRoleReq;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author rylai
 * @since 2018-11-16
 */
public interface ISysRoleService extends IService<SysRole> {
    void addRole(SysRoleReq sysRoleReq);

    SingleRoleAccessesInfo getDetails(Integer id);

    void edit(SysRoleReq sysRoleReq);

    void addBatchRoles(List<SysRoleReq> sysRoleReqs);

    void deleteById(Integer id);

    void deleteByIds(List<Integer> ids);
}
