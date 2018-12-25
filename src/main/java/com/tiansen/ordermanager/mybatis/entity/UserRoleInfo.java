package com.tiansen.ordermanager.mybatis.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleInfo extends SysUser {
    private List<SysRole> sysRole;
}
