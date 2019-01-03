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
 * @since 2019-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderExpress extends Model<OrderExpress> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    private Integer odId;

    private Integer expId;

    private String remark;

    private Integer creatorId;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String OD_ID = "od_id";

    public static final String EXP_ID = "exp_id";

    public static final String REMARK = "remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
