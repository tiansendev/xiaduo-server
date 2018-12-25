package com.tiansen.ordermanager.mybatis.entity;

import lombok.Data;

import java.util.List;


@Data
public class SingleRoleAccessesInfo extends SysRole{
    private List<SysAccess> sysAccess;
    private List<SysMenu> menus;
}
