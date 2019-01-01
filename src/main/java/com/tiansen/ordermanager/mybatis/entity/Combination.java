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
public class Combination extends Model<Combination> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 组合名称
     */
    private String combName;

    /**
     * 产品定义list
     */
    private String prodDefIds;

    private String combRemark;

    private Integer creatorId;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String COMB_NAME = "comb_name";

    public static final String PROD_DEF_IDS = "prod_def_ids";

    public static final String COMB_REMARK = "comb_remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
