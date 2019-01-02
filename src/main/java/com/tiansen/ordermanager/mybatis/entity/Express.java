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
public class Express extends Model<Express> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 收件人
     */
    private String expRecipient;

    /**
     * 手机号
     */
    private String expMobile;

    /**
     * 收件地址
     */
    private String expAddress;

    /**
     * 快递公司
     */
    private String expCompany;

    /**
     * 单号
     */
    private String expOrderCode;

    private String expDescription;

    /**
     * 备注
     */
    private String expRemark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 创建人
     */
    private Integer creatorId;


    public static final String ID = "id";

    public static final String EXP_RECIPIENT = "exp_recipient";

    public static final String EXP_MOBILE = "exp_mobile";

    public static final String EXP_ADDRESS = "exp_address";

    public static final String EXP_COMPANY = "exp_company";

    public static final String EXP_ORDER_CODE = "exp_order_code";

    public static final String EXP_DESCRIPTION = "exp_description";

    public static final String EXP_REMARK = "exp_remark";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    public static final String CREATOR_ID = "creator_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
