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
public class ConsumableDefinition extends Model<ConsumableDefinition> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String conDefName;

    private String conDefCode;

    private String conDefImgUrl;

    private String conDefRemark;

    private Integer creatorId;

    private Date updateDate;

    private Date createDate;


    public static final String ID = "id";

    public static final String CON_DEF_NAME = "con_def_name";

    public static final String CON_DEF_CODE = "con_def_code";

    public static final String CON_DEF_IMG_URL = "con_def_img_url";

    public static final String CON_DEF_REMARK = "con_def_remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String UPDATE_DATE = "update_date";

    public static final String CREATE_DATE = "create_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
