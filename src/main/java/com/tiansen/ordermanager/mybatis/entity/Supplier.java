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
public class Supplier extends Model<Supplier> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 供应商名称
     */
    private String supName;

    /**
     * 供应商地址
     */
    private String supAddr;

    /**
     * 联系人姓名
     */
    private String supContactPerson;

    /**
     * 联系电话
     */
    private String supContactNumber;

    /**
     * 联系人邮箱
     */
    private String supContactEmail;

    private Integer creatorId;

    private Date createDate;

    private Date updateDate;


    public static final String ID = "id";

    public static final String SUP_NAME = "sup_name";

    public static final String SUP_ADDR = "sup_addr";

    public static final String SUP_CONTACT_PERSON = "sup_contact_person";

    public static final String SUP_CONTACT_NUMBER = "sup_contact_number";

    public static final String SUP_CONTACT_EMAIL = "sup_contact_email";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
