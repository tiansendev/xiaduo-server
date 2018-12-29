package com.tiansen.ordermanager.mybatis.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiansen.ordermanager.mybatis.entity.SysUser;
import com.tiansen.ordermanager.mybatis.entity.SysUserReq;
import com.tiansen.ordermanager.mybatis.entity.UserRoleInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author tiansen
 * @since 2018-11-16
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 用户登录
     * @param reqUser
     * @return user & token
     */
    JSONObject login(SysUser reqUser);

    /**
     * 添加普通用户 需管理员权限
     * @param user
     */
    void addUser(SysUserReq user);

    void addUserBatch(List<SysUserReq> sysUserList);

    /**
     * 删除用户 需管理员权限
     * @param id
     */
    void deleteUser(Integer id);

    void deleteBatch(List<Integer> ids);

    /**
     * 查询所有
     * @return user列表
     * @param username
     * @param userMobile
     * @param userCode
     * @param userGender
     * @param pageable
     */
    IPage<UserRoleInfo> findUsersByPage(String username, String userMobile, String userCode, Integer userGender, Pageable pageable);


    /**
     * 查询详情
     * @param id
     * @return
     */
    JSONObject getDetailById(Integer id);


//    /**
//     * 编辑
//     * @param user
//     * @param roleid
//     */
//    void editUser(SysUser user, List<Integer> roleid);

    void editUser(SysUserReq user);

    /**
     * 上传头像
     * @param img
     * @return
     */
    String uploadAvatar(MultipartFile img) throws Exception;

    List<UserRoleInfo> findAll(String username, String userMobile, String userCode, Integer userGender);
}
