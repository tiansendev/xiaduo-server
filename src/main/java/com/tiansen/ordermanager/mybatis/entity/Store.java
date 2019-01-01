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
 * @since 2019-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Store extends Model<Store> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 仓库名称
     */
    private String stoName;

    /**
     * 仓库位置
     */
    private String stoLocation;

    /**
     * 仓库管理员ids：[1, 2]
     */
    private String stoManangers;

    /**
     * 备注
     */
    private String stoRemark;

    private Integer creatorId;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String STO_NAME = "sto_name";

    public static final String STO_LOCATION = "sto_location";

    public static final String STO_MANANGERS = "sto_manangers";

    public static final String STO_REMARK = "sto_remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
