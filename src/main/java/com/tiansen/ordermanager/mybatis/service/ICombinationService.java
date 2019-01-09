package com.tiansen.ordermanager.mybatis.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tiansen.ordermanager.mybatis.entity.Combination;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiansen.ordermanager.mybatis.entity.join.combination.CombinationDefInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    List<Combination> findByCond(String name, String prodName, Sort sort);

    IPage<CombinationDefInfo> findByCondByPage(String name, String prodName, String patternName, Pageable pageable);

    Combination findDetailById(Integer id);
}
