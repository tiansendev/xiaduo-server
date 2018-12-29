package com.tiansen.ordermanager.mybatis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author tiansen
 * @since 2018-11-16
 */
@Data
@Accessors(chain = true)
public class SysRoleReq extends SysRole{

    private static final long serialVersionUID = 1L;

//    private SysRole sysRole;
    private List<Integer> accessIds;
}
