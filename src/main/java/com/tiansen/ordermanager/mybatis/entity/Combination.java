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
public class Combination extends Model<Combination> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 组合名称
     */
    private String combName;

    /**
     * 产品list
     */
    private String combProdIds;

    /**
     * 盒数
     */
    private Integer combBoxNum;

    private String combRemark;

    private Integer combCreatorId;

    private Date combCreatetime;

    private Date combUpdatetime;


    public static final String ID = "id";

    public static final String COMB_NAME = "comb_name";

    public static final String COMB_PROD_IDS = "comb_prod_ids";

    public static final String COMB_BOX_NUM = "comb_box_num";

    public static final String COMB_REMARK = "comb_remark";

    public static final String COMB_CREATOR_ID = "comb_creator_id";

    public static final String COMB_CREATETIME = "comb_createtime";

    public static final String COMB_UPDATETIME = "comb_updatetime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
