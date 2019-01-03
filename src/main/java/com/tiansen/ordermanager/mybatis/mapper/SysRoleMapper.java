package com.tiansen.ordermanager.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiansen.ordermanager.mybatis.entity.SingleRoleAccessesInfo;
import com.tiansen.ordermanager.mybatis.entity.SysRole;
import com.tiansen.ordermanager.mybatis.entity.SysRoleReq;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author rylai
 * @since 2018-11-16
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
    SingleRoleAccessesInfo findSysRoleAccessInfosByRoleId(Integer id);

    Integer updateRoleById(SysRoleReq sysRole);
}
