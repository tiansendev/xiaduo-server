package com.tiansen.ordermanager.mybatis.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tiansen.ordermanager.mybatis.entity.Combination;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiansen.ordermanager.mybatis.entity.join.CombinationDetail;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
public interface ICombinationService extends IService<Combination> {

    List<Combination> findByCond(String name, String prodName);

    IPage<Combination> findByCondByPage(String name, String prodName, Pageable pageable);

    Combination findDetailById(Integer id);
}
