package com.tiansen.ordermanager.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiansen.ordermanager.mybatis.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author rylai
 * @since 2018-11-16
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<SysUser> selectByUserName(@Param("user_name") String user_name);
}
