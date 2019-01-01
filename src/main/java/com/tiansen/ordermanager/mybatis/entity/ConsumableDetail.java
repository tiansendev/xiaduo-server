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
 * 耗材
 * </p>
 *
 * @author rylai
 * @since 2019-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConsumableDetail extends Model<ConsumableDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer conDefId;

    private Integer purId;

    private Integer orderId;

    private Integer storeId;

    private String conRemark;

    private Integer creatorId;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String CON_DEF_ID = "con_def_id";

    public static final String PUR_ID = "pur_id";

    public static final String ORDER_ID = "order_id";

    public static final String STORE_ID = "store_id";

    public static final String CON_REMARK = "con_remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
