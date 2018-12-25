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
 * 模块权限
 * </p>
 *
 * @author rylai
 * @since 2018-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysAccess extends Model<SysAccess> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 请求URL正则
     */
    private String accRequrl;

    private String accMethod;

    /**
     * 权限描述
     */
    private String accDisp;

    /**
     * 模块操作描述
     */
    private String accModuleDisp;

    private LocalDateTime updateDate;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;


    public static final String ID = "id";

    public static final String ACC_REQURL = "acc_requrl";

    public static final String ACC_METHOD = "acc_method";

    public static final String ACC_DISP = "acc_disp";

    public static final String ACC_MODULE_DISP = "acc_module_disp";

    public static final String UPDATE_DATE = "update_date";

    public static final String CREATE_DATE = "create_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
