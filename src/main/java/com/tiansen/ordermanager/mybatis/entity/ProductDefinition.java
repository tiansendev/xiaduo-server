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
public class ProductDefinition extends Model<ProductDefinition> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 产品名称
     */
    private String prodDefName;

    /**
     * 产品货号
     */
    private String prodDefNo;

    /**
     * 产品规格
     */
    private String prodDefSpec;

    /**
     * 产品当前销售价格
     */
    private Double prodDefSalePrice;

    /**
     * 产品类型 0：盈利产品 1：消耗品 2：赠品 3：非卖品 4：其他
     */
    private Integer prodDefType;

    /**
     * 产品图片
     */
    private String prodDefImgUrl;

    /**
     * 备注
     */
    private String prodDefRemark;

    private Integer creatorId;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String PROD_DEF_NAME = "prod_def_name";

    public static final String PROD_DEF_TYPE = "prod_def_type";

    public static final String PROD_DEF_NO = "prod_def_no";

    public static final String PROD_DEF_SPEC = "prod_def_spec";

    public static final String PROD_DEF_SALE_PRICE = "prod_def_sale_price";

    public static final String PROD_DEF_IMG_URL = "prod_def_img_url";

    public static final String PROD_DEF_REMARK = "prod_def_remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
