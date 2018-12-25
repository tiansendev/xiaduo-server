package com.tiansen.ordermanager.mybatis.entity;

import lombok.Data;


@Data
public class SysRoleAccessInfo extends SysRoleAccess {
    private SysRole sysRole;
    private SysAccess sysAccess;
}
