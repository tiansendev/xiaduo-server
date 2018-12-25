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
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 组合id
     */
    private String odCombId;

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
    private String odOwner;

    /**
     * 快递id
     */
    private String odExpressId;

    private String odRemark;

    private Date odCreatedate;

    private Date odUpdatedate;

    /**
     * 创建人
     */
    private Integer odCreatorId;


    public static final String ID = "id";

    public static final String OD_COMB_ID = "od_comb_id";

    public static final String OD_COMB_NUM = "od_comb_num";

    public static final String OD_BOX_NUM = "od_box_num";

    public static final String OD_OWNER = "od_owner";

    public static final String OD_EXPRESS_ID = "od_express_id";

    public static final String OD_REMARK = "od_remark";

    public static final String OD_CREATEDATE = "od_createdate";

    public static final String OD_UPDATEDATE = "od_updatedate";

    public static final String OD_CREATOR_ID = "od_creator_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
