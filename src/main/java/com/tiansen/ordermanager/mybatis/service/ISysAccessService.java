package com.tiansen.ordermanager.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiansen.ordermanager.mybatis.entity.SysAccess;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 模块权限 服务类
 * </p>
 *
 * @author tiansen
 * @since 2018-11-16
 */
@Repository("sysAccessService")
public interface ISysAccessService extends IService<SysAccess> {

}
