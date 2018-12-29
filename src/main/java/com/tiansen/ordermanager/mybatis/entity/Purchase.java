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
 * 采购
 * </p>
 *
 * @author rylai
 * @since 2018-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Purchase extends Model<Purchase> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 编号
     */
    private String purCode;

    /**
     * 采购总价
     */
    private Double purTotalMoney;

    /**
     * 供应商id
     */
    private Integer supplierId;

    /**
     * 采购日期
     */
    private Date purDate;

    private String purRemark;

    private Integer creatorId;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String PUR_CODE = "pur_code";

    public static final String PUR_TOTAL_MONEY = "pur_total_money";

    public static final String SUPPLIER_ID = "supplier_id";

    public static final String PUR_DATE = "pur_date";

    public static final String PUR_REMARK = "pur_remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
