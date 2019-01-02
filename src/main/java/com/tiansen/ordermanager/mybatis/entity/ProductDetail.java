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
public class ProductDetail extends Model<ProductDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 产品定义id
     */
    private Integer pddefId;

    /**
     * 产品状态   0:在库 1: 正在发货途中 2: 已收货  9：退回
     */
    private Integer prodStatus;

    /**
     * 采购id
     */
    private Integer purId;

    private Double purPrice;

    /**
     * 仓库id
     */
    private Integer storeId;

    /**
     * 订单id
     */
    private Integer orderId;

    private Double salePrice;

    private Integer creatorId;

    private String prodRemark;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String PDDEF_ID = "pddef_id";

    public static final String PROD_STATUS = "prod_status";

    public static final String PUR_ID = "pur_id";

    public static final String PUR_PRICE = "pur_price";

    public static final String STORE_ID = "store_id";

    public static final String ORDER_ID = "order_id";

    public static final String SALE_PRICE = "sale_price";

    public static final String CREATOR_ID = "creator_id";

    public static final String PROD_REMARK = "prod_remark";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
