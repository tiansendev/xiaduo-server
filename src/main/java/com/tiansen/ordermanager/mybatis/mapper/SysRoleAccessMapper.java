package com.tiansen.ordermanager.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiansen.ordermanager.mybatis.entity.SingleRoleAccessesInfo;
import com.tiansen.ordermanager.mybatis.entity.SysRoleAccess;
import com.tiansen.ordermanager.mybatis.entity.SysRoleAccessInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 企业角色权限表 Mapper 接口
 * </p>
 *
 * @author tiansen
 * @since 2018-11-16
 */
@Repository
public interface SysRoleAccessMapper extends BaseMapper<SysRoleAccess> {

    List<SysRoleAccessInfo> findAllSysRoleAccessInfos();

    List<SysRoleAccessInfo> findAllSysRoleAccessInfosByRoleIds(List<Integer> userid);

    void insertBatch(List<SysRoleAccess> sysRoleAccesses);

    SingleRoleAccessesInfo findSysRoleAccessInfosByRoleId(Integer id);
}
