package com.tiansen.ordermanager.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 资源路由表
 * </p>
 *
 * @author tiansen
 * @since 2018-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String menuName;

    /**
     * 资源路径
     */
    private String menuUrl;

    /**
     * 资源介绍
     */
        private String menuDescription;

    /**
     * 资源图标
     */
    private String menuIcon;

    /**
     * 父级资源id
     */
    private Long menuSuperid;

    /**
     * 排序
     */
    private Integer menuSeq;

    /**
     * 0:所有企业都支持的模块
1:特别功能
2:暂未定义
     */
    private Integer menuStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    private LocalDateTime updateDate;


    public static final String ID = "id";

    public static final String MENU_NAME = "menu_name";

    public static final String MENU_URL = "menu_url";

    public static final String MENU_DESCRIPTION = "menu_description";

    public static final String MENU_ICON = "menu_icon";

    public static final String MENU_SUPERID = "menu_superid";

    public static final String MENU_SEQ = "menu_seq";

    public static final String MENU_STATUS = "menu_status";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
