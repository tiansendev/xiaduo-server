package com.tiansen.ordermanager.mybatis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiansen.ordermanager.common.aop.RefreshShiroCache;
import com.tiansen.ordermanager.common.code.ServiceResultCode;
import com.tiansen.ordermanager.common.util.ImgUploadUtils;
import com.tiansen.ordermanager.common.util.RylaiRandom;
import com.tiansen.ordermanager.config.cache.EhcacheService;
import com.tiansen.ordermanager.exception.BusinessException;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.*;
import com.tiansen.ordermanager.mybatis.mapper.SysMenuMapper;
import com.tiansen.ordermanager.mybatis.mapper.SysRoleAccessMapper;
import com.tiansen.ordermanager.mybatis.mapper.SysUserMapper;
import com.tiansen.ordermanager.mybatis.mapper.SysUserRoleMapper;
import com.tiansen.ordermanager.mybatis.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author tiansen
 * @since 20181116
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Value("${img.avatar}")
    private String imgPath;

    @Autowired
    private EhcacheService ehcacheService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleAccessMapper sysRoleAccessMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public JSONObject login(SysUser reqUser) {

        String username = reqUser.getUserName();
        String password = reqUser.getUserPassword();

        if (StringUtils.isAnyBlank(username, password)) {
            throw new ParameterIllegalException("用户名、密码不能为空");
        }

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        currentUser.login(usernamePasswordToken);

        // 生成token，缓存 sessionId
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        ehcacheService.set(token, currentUser.getSession().getId());

        SysUser user = (SysUser) currentUser.getPrincipal();

        user.setUserPassword(null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("user", user);

        return jsonObject;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(SysUserReq user) {
        List<Integer> roleids = user.getRoleids();
        if (user == null
                || roleids == null
                || roleids.size() == 0
                || StringUtils.isAnyBlank(
                user.getUserName(),
                user.getUserPassword())) {
            throw new ParameterIllegalException("用户名、密码不能为空");
        }

        SysUser u = getOne(new QueryWrapper<SysUser>().eq(SysUser.USER_NAME, user.getUserName()));
        if (u != null) {
            throw new BusinessException(ServiceResultCode.USER_HAD_EXIST.getCode());
        }

        LocalDateTime currDate = LocalDateTime.now();
        user.setCreateDate(currDate)
                .setUpdateDate(currDate);
        save(user);

        List<SysUserRole> userRoles = new ArrayList<>();
        for (Integer roleid : roleids) {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(roleid)
                    .setUserId(user.getId())
                    .setCreateDate(currDate);
            userRoles.add(userRole);
        }
        sysUserRoleMapper.insertBatch(userRoles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserBatch(List<SysUserReq> usersReq) {
        LocalDateTime currDate = LocalDateTime.now();

        List<SysUser> users = new ArrayList<>();
        List<SysUserRole> userRoles = new ArrayList<>();
        for (SysUserReq userReq : usersReq) {
            List<Integer> roleids = userReq.getRoleids();
            if (userReq == null
                    || roleids == null
                    || StringUtils.isAnyBlank(userReq.getUserName(), userReq.getUserPassword())) {
                throw new ParameterIllegalException("用户名、密码或角色不能为空");
            }
            userReq.setCreateDate(currDate);
            users.add(userReq);

        }
        saveBatch(users);

        // sysuserRole
        for (SysUserReq userReq :usersReq) {
            List<Integer> roleids = userReq.getRoleids();
            for (Integer roleid : roleids) {
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(roleid)
                        .setUserId(userReq.getId())
                        .setCreateDate(currDate);
                userRoles.add(userRole);
            }
        }

        sysUserRoleMapper.insertBatch(userRoles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer id) {
        if (id == null)
            throw new ParameterIllegalException("用户或id为空");
        removeById(id);

        // 删除user_role中间表
        sysUserRoleMapper.delete(
                new QueryWrapper<SysUserRole>()
                        .eq(SysUserRole.USER_ID, id)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Integer> ids) {
        if (ids == null)
            throw new ParameterIllegalException("用户或id为空");
        removeByIds(ids);

        // 删除user_role中间表
        sysUserRoleMapper.delete(
                new QueryWrapper<SysUserRole>()
                        .in(SysUserRole.USER_ID, ids)
        );
    }

    @Override
    public IPage<UserRoleInfo> findUsersByPage(String username, String userMobile, String userCode, Integer userGender, Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        map.put(SysUser.USER_NAME, username);
        map.put(SysUser.USER_MOBILE, userMobile);
        map.put(SysUser.USER_CODE, userCode);
        map.put(SysUser.USER_GENDER, userGender);
        Page<UserRoleInfo> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        return  sysUserRoleMapper.findAllUserRoles(page, map);
    }

    @Override
    public List<UserRoleInfo> findAll(String username, String userMobile, String userCode, Integer userGender) {
        Map<String, Object> map = new HashMap<>();
        map.put(SysUser.USER_NAME, username);
        map.put(SysUser.USER_MOBILE, userMobile);
        map.put(SysUser.USER_CODE, userCode);
        map.put(SysUser.USER_GENDER, userGender);
        return sysUserRoleMapper.findAllUserRoles(map);
    }

    @Override
    public JSONObject getDetailById(Integer id) {
        UserRoleInfo info = sysUserRoleMapper.findUserRolesByUserId(id);
        if (info == null)
            return null;

        // 根据menuids查询muenus
        List<SysMenu> menus = null;
        Set<Integer> menuIds = new HashSet<>();
        List<Integer> roleIds = new ArrayList<>();
        if (info.getSysRole() != null && info.getSysRole() != null) {
            for (SysRole role : info.getSysRole()) {
                Integer[] mids = (Integer[])role.getMenuIds();
                menuIds.addAll(Arrays.asList(mids));

                if (role == null || StringUtils.isBlank(role.getRoleName()))
                    continue;
                roleIds.add(role.getId());
            }
            menus = sysMenuMapper.selectBatchIds(menuIds);
        }

        // 根据roleids查询permisions
        List<SysRoleAccessInfo> sysRoleAccessInfos = null;
        List<SysAccess> perms = new ArrayList<>();
        if (info.getSysRole() != null) {
            sysRoleAccessInfos = sysRoleAccessMapper.findAllSysRoleAccessInfosByRoleIds(roleIds);
            for (SysRoleAccessInfo roleAccessInfo : sysRoleAccessInfos) {
                if (roleAccessInfo == null || roleAccessInfo.getSysAccess() == null || roleAccessInfo.getSysRole() == null
                        || StringUtils.isAnyBlank(roleAccessInfo.getSysRole().getRoleName(), roleAccessInfo.getSysAccess().getAccMethod()))
                    continue;
                perms.add(roleAccessInfo.getSysAccess());
            }
        }

        JSONObject object = new JSONObject();
        object.put("userRoleInfo", info);
        object.put("menus", menus);
        object.put("permissions", perms);

        return object;
    }

    @Override
    @RefreshShiroCache(paramIndex = 0)
    @Transactional(rollbackFor = Exception.class)
    public void editUser(SysUserReq user) {
        if (user == null ||
                user.getId() == null ||
                StringUtils.isAnyBlank(
                        user.getUserName())) {
            throw new ParameterIllegalException("用户id、用户名、密码不能为空");
        }

        // 修改用户角色信息
        List<Integer> roleids = user.getRoleids();
        if (roleids != null && roleids.size() != 0){
            // 修改前的ids
            List<Integer> orignIds = new ArrayList<>();
            List<SysUserRole> orignSysUserRoles
                    = sysUserRoleMapper.selectList(
                    new QueryWrapper<SysUserRole>().eq(SysUserRole.USER_ID, user.getId()));
            for (SysUserRole userRole : orignSysUserRoles) {
                orignIds.add(userRole.getRoleId());
            }
            // copy 当前角色数据
            List<Integer> removeIds = new ArrayList<>(orignIds);

            // 需要修改的ids
            List<Integer> addIds = roleids;

            // need remove
            removeIds.removeAll(addIds);

            // need add
            addIds.removeAll(orignIds);

            // 添加
            List<SysUserRole> addedUserRoles = new ArrayList<>();
            for (Integer accid : addIds) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(accid)
                        .setUserId(user.getId())
                        .setCreateDate(LocalDateTime.now());
                addedUserRoles.add(sysUserRole);
            }

            if (addedUserRoles.size() > 0)
                sysUserRoleMapper.insertBatch(addedUserRoles);

            // 删除
            if (removeIds.size() > 0)
                sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().in(SysUserRole.ROLE_ID, removeIds));
        }

        updateById(user);
    }

    @Override
    public String uploadAvatar(MultipartFile img) throws Exception {
        String filename = RylaiRandom.getUUid()+".jpg";
        String filepath =imgPath+"/"+filename;
        String avatarUrl = "load/img/avatar/" + filename;
        ImgUploadUtils.uploadImgs(img, filepath);
        return avatarUrl;
    }


}
