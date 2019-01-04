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
 * 产品定义-组合中间表
 * </p>
 *
 * @author rylai
 * @since 2019-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CombinationProductDef extends Model<CombinationProductDef> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 组合id
     */
    private Integer combId;

    /**
     * 产品定义id
     */
    private Integer prodDefId;

    /**
     * 产品数量
     */
    private Integer prodNum;

    /**
     * 单位
     */
    private String prodUnit;

    /**
     * 备注
     */
    private String combDefRemark;

    private Integer creatorId;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String COMB_ID = "comb_id";

    public static final String PROD_DEF_ID = "prod_def_id";

    public static final String PROD_NUM = "prod_num";

    public static final String PROD_UNIT = "prod_unit";

    public static final String COMB_DEF_REMARK = "comb_def_remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
