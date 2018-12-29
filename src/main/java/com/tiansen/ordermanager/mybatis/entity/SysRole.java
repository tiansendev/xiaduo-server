package com.tiansen.ordermanager.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author tiansen
 * @since 2018-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRole{

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 状态0：启用，1禁用
     */
    private Integer roleStatus;

    /**
     * 角色支持的模块，也及左侧菜单
     */
//    @TableField(el = "menuIds, typeHandler=com.tiansen.ordermanager.mybatis.handler.MybatisJsonHandler")
    private Object[] menuIds;

    /**
     * 简介
     */
    private String roleRemark;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    private LocalDateTime updateDate;


    public static final String ID = "id";

    public static final String ROLE_NAME = "role_name";

    public static final String ROLE_STATUS = "role_status";

    public static final String MENU_IDS = "menu_ids";

    public static final String ROLE_REMARK = "role_remark";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

}
