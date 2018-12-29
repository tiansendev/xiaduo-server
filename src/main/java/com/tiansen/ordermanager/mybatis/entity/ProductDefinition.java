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
 * @since 2018-12-30
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
    private String defName;

    /**
     * 产品货号
     */
    private String defNo;

    /**
     * 产品规格
     */
    private String prodSpec;

    /**
     * 产品图片
     */
    private String defImgUrl;

    /**
     * 备注
     */
    private String defRemark;

    private Integer creatorId;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String DEF_NAME = "def_name";

    public static final String DEF_NO = "def_no";

    public static final String PROD_SPEC = "prod_spec";

    public static final String DEF_IMG_URL = "def_img_url";

    public static final String DEF_REMARK = "def_remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
