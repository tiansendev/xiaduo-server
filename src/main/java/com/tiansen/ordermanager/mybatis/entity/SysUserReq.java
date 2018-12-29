package com.tiansen.ordermanager.mybatis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author tiansen
 * @since 2018-11-16
 */
@Data
@Accessors(chain = true)
public class SysUserReq extends SysUser{

    private static final long serialVersionUID = 1L;

//    private SysUser sysUser;

    private List<Integer> roleids;

}
