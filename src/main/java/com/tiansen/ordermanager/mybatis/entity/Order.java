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
 * @since 2019-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单日期
     */
    private String odDate;

    /**
     * 组合id
     */
    private Integer odCombId;

    /**
     * 组数
     */
    private Integer odCombNum;

    /**
     * 合数
     */
    private Integer odBoxNum;

    /**
     * 申请人
     */
    private Integer propId;

    private String consumIds;

    private String odRemark;

    /**
     * 创建人
     */
    private Integer creatorId;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String OD_DATE = "od_date";

    public static final String OD_COMB_ID = "od_comb_id";

    public static final String OD_COMB_NUM = "od_comb_num";

    public static final String OD_BOX_NUM = "od_box_num";

    public static final String PROP_ID = "prop_id";

    public static final String CONSUM_IDS = "consum_ids";

    public static final String OD_REMARK = "od_remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
