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
public class Proposer extends Model<Proposer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String propName;

    private String propContactMan;

    private String propContactNumber;

    /**
     * 申请人性质：0：平台 1：个人
     */
    private String propProperty;

    private String propRemark;

    private String creatorId;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String PROP_NAME = "prop_name";

    public static final String PROP_CONTACT_MAN = "prop_contact_man";

    public static final String PROP_CONTACT_NUMBER = "prop_contact_number";

    public static final String PROP_PROPERTY = "prop_property";

    public static final String PROP_REMARK = "prop_remark";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
