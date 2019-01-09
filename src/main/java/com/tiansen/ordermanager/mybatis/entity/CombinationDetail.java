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
 * 组合详情
 * </p>
 *
 * @author rylai
 * @since 2019-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CombinationDetail extends Model<CombinationDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer odId;

    private Integer combId;

    private Double combSalePrice;

    private String combDetailRemak;

    private Date updateDate;

    private Integer creatorId;

    private Date createDate;


    public static final String ID = "id";

    public static final String OD_ID = "od_id";

    public static final String COMB_ID = "comb_id";

    public static final String COMB_SALE_PRICE = "comb_sale_price";

    public static final String COMB_DETAIL_REMAK = "comb_detail_remak";

    public static final String UPDATE_DATE = "update_date";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
