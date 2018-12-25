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
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 产品名称
     */
    private String prodName;

    /**
     * 产品货号
     */
    private String prodNo;

    /**
     * 产品规格
     */
    private String prodSpec;

    /**
     * 备注
     */
    private String prodRemark;

    private Integer prodCreatorId;

    private Date prodCreatetime;

    private Date prodUpdatetime;


    public static final String ID = "id";

    public static final String PROD_NAME = "prod_name";

    public static final String PROD_NO = "prod_no";

    public static final String PROD_SPEC = "prod_spec";

    public static final String PROD_REMARK = "prod_remark";

    public static final String PROD_CREATOR_ID = "prod_creator_id";

    public static final String PROD_CREATETIME = "prod_createtime";

    public static final String PROD_UPDATETIME = "prod_updatetime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
