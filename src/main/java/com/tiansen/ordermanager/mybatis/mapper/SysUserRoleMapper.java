package com.tiansen.ordermanager.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansen.ordermanager.mybatis.entity.SysUserRole;
import com.tiansen.ordermanager.mybatis.entity.UserRoleInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色 Mapper 接口
 * </p>
 *
 * @author rylai
 * @since 2018-11-16
 */
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    UserRoleInfo findUserRolesByUserId(Integer userid);

    void insertBatch(List<SysUserRole> sysUserRoles);

    List<UserRoleInfo> findAllUserRoles(@Param("condMap") Map<String, Object> map);

    Page<UserRoleInfo> findAllUserRoles(Page<UserRoleInfo> page, @Param("condMap") Map<String, Object> map);
}
