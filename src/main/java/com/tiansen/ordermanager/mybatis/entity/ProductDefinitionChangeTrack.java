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
 * 产品定义属性变化记录
 * </p>
 *
 * @author rylai
 * @since 2019-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProductDefinitionChangeTrack extends Model<ProductDefinitionChangeTrack> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer prodDefId;

    /**
     * 所改变的属性：0：价格 1：名称
     */
    private Integer changeProperty;

    private Double changePriceLast;

    private Double changePriceCurrent;

    private String changeNameLast;

    private String changeNameCurrent;

    private String changeRemark;

    private String creatorId;

    private Date updateDate;

    private Date createDate;


    public static final String ID = "id";

    public static final String PROD_DEF_ID = "prod_def_id";

    public static final String CHANGE_PROPERTY = "change_property";

    public static final String CHANGE_PRICE_LAST = "change_price_last";

    public static final String CHANGE_PRICE_CURRENT = "change_price_current";

    public static final String CHANGE_NAME_LAST = "change_name_last";

    public static final String CHANGE_NAME_CURRENT = "change_name_current";

    public static final String CHANGE_REMARK = "change_remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String UPDATE_DATE = "update_date";

    public static final String CREATE_DATE = "create_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
