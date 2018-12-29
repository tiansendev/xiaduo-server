package com.tiansen.ordermanager.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author tiansen
 * @since 2018-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登陆名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 手机号
     */
    private String userMobile;

    /**
     * 员工编号
     */
    private String userCode;

    /**
     * 头像
     */
    private String userAvatar;

    /**
     * 性别
     */
    private Integer userGender;

    /**
     * 年龄
     */
    private Integer userAge;

    /**
     * 用户状态
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    private LocalDateTime updateDate;


    public static final String ID = "id";

    public static final String USER_NAME = "user_name";

    public static final String USER_PASSWORD = "user_password";

    public static final String USER_MOBILE = "user_mobile";

    public static final String USER_CODE = "user_code";

    public static final String USER_AVATAR = "user_avatar";

    public static final String USER_GENDER = "user_gender";

    public static final String USER_AGE = "user_age";

    public static final String USER_STATUS = "user_status";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
