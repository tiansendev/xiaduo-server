package com.tiansen.ordermanager.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author rylai
 * @since 2018-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Platform extends Model<Platform> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 平台名称
     */
    private String plaName;

    /**
     * 平台负责人
     */
    private String platResponsibleMan;

    /**
     * 联系电话
     */
    private String platMobile;

    private String platRemark;

    private Integer platCreatorId;

    private Date platCreatetime;

    private Date platUpdatetime;


    public static final String ID = "id";

    public static final String PLA_NAME = "pla_name";

    public static final String PLAT_RESPONSIBLE_MAN = "plat_responsible_man";

    public static final String PLAT_MOBILE = "plat_mobile";

    public static final String PLAT_REMARK = "plat_remark";

    public static final String PLAT_CREATOR_ID = "plat_creator_id";

    public static final String PLAT_CREATETIME = "plat_createtime";

    public static final String PLAT_UPDATETIME = "plat_updatetime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
