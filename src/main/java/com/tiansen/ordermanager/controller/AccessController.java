package com.tiansen.ordermanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.SysAccess;
import com.tiansen.ordermanager.mybatis.entity.SysAccessInfo;
import com.tiansen.ordermanager.mybatis.entity.SysRoleAccess;
import com.tiansen.ordermanager.mybatis.mapper.SysRoleAccessMapper;
import com.tiansen.ordermanager.mybatis.service.ISysAccessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Api(value="权限接口")
@RequestMapping("/access")
public class AccessController {

    @Autowired
    private ISysAccessService sysAccessService;
    @Autowired
    private SysRoleAccessMapper sysRoleAccessMapper;

    @ApiOperation(value="添加权限" ,notes="")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResult addAccess(@RequestBody SysAccess sysAccess) {
        if (sysAccess == null || StringUtils.isAnyBlank(sysAccess.getAccMethod(), sysAccess.getAccRequrl()))
            throw new ParameterIllegalException();

        sysAccessService.save(sysAccess);
        return ServiceResult.success();
    }

    @ApiOperation(value="批量添加" ,notes="")
    @RequestMapping(value = "/addbatch", method = RequestMethod.POST)
    public ServiceResult addBatch(@RequestBody List<SysAccess> sysAccesses) {
        if (sysAccesses == null)
            throw new ParameterIllegalException();

        sysAccessService.saveBatch(sysAccesses);
        return ServiceResult.success();
    }

    @ApiOperation(value="根据id获取权限" ,notes="")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServiceResult<SysAccess> findById(@PathVariable("id") Integer id) {
        if (id == null) {
            throw new ParameterIllegalException("id不能为空");
        }
        return ServiceResult.success(sysAccessService.getById(id));
    }

//    @ApiOperation(value="获取权限" ,notes="")
//    @RequestMapping(value = "/bypage", method = RequestMethod.GET)
//    public ServiceResult<?> find(
//            @RequestParam(value = "AccessReqUrl", required = false) String AccessReqUrl,
//            @RequestParam(value = "AccessMethod", required = false) Integer AccessMethod,
//            @PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.ASC)Pageable pageable
//    ) {
//
//        QueryWrapper<SysAccess> wrapper = new QueryWrapper<>();
//        if (!StringUtils.isBlank(AccessReqUrl)) {
//            wrapper.eq(SysAccess.ACC_REQURL, AccessReqUrl);
//        }
//
//        if (AccessMethod != null) {
//            wrapper.eq(SysAccess.ACC_METHOD, AccessMethod);
//        }
//
//        Page page = new Page(pageable.getPageNumber(), pageable.getPageSize());
//        return ServiceResult.success(sysAccessService.page(page, wrapper));
//    }

    @ApiOperation(value="获取权限" ,notes="")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ServiceResult<?> find(
            @RequestParam(value = "AccessReqUrl", required = false) String AccessReqUrl,
            @RequestParam(value = "AccessMethod", required = false) Integer AccessMethod
    ) {
        QueryWrapper<SysAccess> wrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(AccessReqUrl)) {
            wrapper.eq(SysAccess.ACC_REQURL, AccessReqUrl);
        }

        if (AccessMethod != null) {
            wrapper.eq(SysAccess.ACC_METHOD, AccessMethod);
        }
        List<SysAccess> list = sysAccessService.list(wrapper);

        // 转换存到map中
        Map<String, Set<SysAccess>> map = new HashMap<>();
        for (SysAccess sysAccess : list) {
            String accessAccModuleDisp = sysAccess.getAccModuleDisp();
            sysAccess.setAccModuleDisp(null)
                    .setAccRequrl(null)
                    .setUpdateDate(null)
                    .setCreateDate(null)
                    .setAccMethod(null);

            if (map.get(accessAccModuleDisp) == null) {
                Set<SysAccess> sysAccessSet = new HashSet<>();
                sysAccessSet.add(sysAccess);
                map.put(accessAccModuleDisp, sysAccessSet);
            } else {
                map.get(accessAccModuleDisp).add(sysAccess);
            }
        }

        // 转换成list
        List<SysAccessInfo> sysAccessInfos = new ArrayList<>();
        map.keySet().forEach(key -> {
            SysAccessInfo info = new SysAccessInfo();
            info.setAccDisp(key);
            info.setSysAccesses(map.get(key));
            sysAccessInfos.add(info);
        });

        return ServiceResult.success(sysAccessInfos);
    }

    @ApiOperation(value="修改权限信息" ,notes="")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ServiceResult<?> edit(@RequestBody SysAccess sysAccess) {

        if (sysAccess ==  null || sysAccess.getId() == null
                || StringUtils.isAnyBlank(sysAccess.getAccMethod(), sysAccess.getAccRequrl())) {
            throw new ParameterIllegalException();
        }

        QueryWrapper<SysAccess> wrapper = new QueryWrapper<SysAccess>()
                .eq(SysAccess.ID, sysAccess.getId());

        sysAccessService.update(sysAccess, wrapper);
        return ServiceResult.success();
    }

    @ApiOperation(value="删除权限信息" ,notes="")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ServiceResult<?> delete(@PathVariable("id") Integer id) {
        if (id ==  null) {
            throw new ParameterIllegalException();
        }
        sysAccessService.removeById(id);
        sysRoleAccessMapper.delete(new QueryWrapper<SysRoleAccess>()
                .eq(SysRoleAccess.ACCESS_ID, id));
        return ServiceResult.success();
    }

    @ApiOperation(value="批量删除权限信息" ,notes="")
    @RequestMapping(value = "/delbatch", method = RequestMethod.DELETE)
    public ServiceResult<?> deleteBatch(@RequestBody List<Integer> ids) {

        if (ids ==  null) {
            throw new ParameterIllegalException();
        }
        sysAccessService.removeByIds(ids);

        sysRoleAccessMapper.delete(new QueryWrapper<SysRoleAccess>()
                .in(SysRoleAccess.ACCESS_ID, ids));
        return ServiceResult.success();
    }
}
