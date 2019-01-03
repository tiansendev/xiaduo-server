package com.tiansen.ordermanager.controller;


import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.Combination;
import com.tiansen.ordermanager.mybatis.fill.CreateFieldFill;
import com.tiansen.ordermanager.mybatis.service.ICombinationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rylai
 * @since 2019-01-02
 */
@RestController
@Api(value = "组合管理API接口",tags = {"组合管理API接口"})
@RequestMapping("/combination")
public class CombinationController {
    @Autowired
    private ICombinationService iCombinationService;

    @ApiOperation(value = "添加组合", notes = "添加组合")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResult addComb(@RequestBody Combination combination) throws Exception {
        if (combination == null || StringUtils.isBlank(combination.getCombName())
                || combination.getProdDefIds() == null || combination.getProdDefIds().length == 0)
            throw  new ParameterIllegalException("参数不合法");
        CreateFieldFill.fill(combination);
        iCombinationService.save(combination);
        return ServiceResult.success();
    }

    @ApiOperation(value = "删除组合", notes = "删除组合")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServiceResult delComb(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        iCombinationService.removeById(id);
        return ServiceResult.success();
    }

    @ApiOperation(value = "删除组合", notes = "删除组合")
    @RequestMapping(value = "/delbatch", method = RequestMethod.DELETE)
    public ServiceResult delBatchComb(@RequestBody List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        iCombinationService.removeByIds(ids);
        return ServiceResult.success();
    }

    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServiceResult findById(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        return ServiceResult.success(iCombinationService.getById(id));
    }

    @ApiOperation(value = "查询组合", notes = "查询组合")
    @RequestMapping(value = "/byids", method = RequestMethod.GET)
    public ServiceResult findByIds(@RequestParam(value = "ids")  List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        return ServiceResult.success(iCombinationService.listByIds(ids));
    }

    @ApiOperation(value = "条件查询", notes = "条件查询")
    @RequestMapping(value = "/bypage", method = RequestMethod.GET)
    public ServiceResult findByCondByPage(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "prodname", required = false) String prodName,
            @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.ASC)Pageable pageable
    ) throws Exception {
        return ServiceResult.success(iCombinationService.findByCondByPage(name, prodName, pageable));
    }

    @ApiOperation(value = "条件查询", notes = "条件查询")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ServiceResult findByCond(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "prodname", required = false) String prodName
    ) throws Exception {
        return ServiceResult.success(iCombinationService.findByCond(name, prodName));
    }

    @ApiOperation(value = "查看详情", notes = "条件查询")
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public ServiceResult findByCond(
            @PathVariable("id") Integer id
    ) throws Exception {
        return ServiceResult.success(iCombinationService.findDetailById(id));
    }

    @ApiOperation(value = "查看详情", notes = "条件查询")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ServiceResult update(@RequestBody Combination combination, @PathVariable("id")Integer id) throws Exception {
        if (combination == null || combination.getId() == null)
            throw new ParameterIllegalException();
        iCombinationService.updateById(combination);
        return ServiceResult.success();
    }

}
