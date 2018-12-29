package com.tiansen.ordermanager.mybatis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * <p>
 * 模块权限
 * </p>
 *
 * @author tiansen
 * @since 2018-11-16
 */
@Data
@Accessors(chain = true)
public class SysAccessInfo {
    private static final long serialVersionUID = 1L;
    private Set<SysAccess> sysAccesses;
    private String accDisp;
}
