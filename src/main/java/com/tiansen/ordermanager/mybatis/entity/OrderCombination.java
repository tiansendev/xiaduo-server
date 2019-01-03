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
 * 订单-组合中间表
 * </p>
 *
 * @author rylai
 * @since 2019-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderCombination extends Model<OrderCombination> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer odId;

    private Integer combId;

    /**
     * 组合数量
     */
    private Integer combNum;

    private String remak;

    private Date updateDate;

    private Integer creatorId;

    private Date createDate;


    public static final String ID = "id";

    public static final String OD_ID = "od_id";

    public static final String COMB_ID = "comb_id";

    public static final String COMB_NUM = "comb_num";

    public static final String REMAK = "remak";

    public static final String UPDATE_DATE = "update_date";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
