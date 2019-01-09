package com.tiansen.ordermanager.controller;


import com.tiansen.ordermanager.common.model.ServiceResult;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.ProductDetailInStore;
import com.tiansen.ordermanager.mybatis.service.IProductInStoreDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "产品详情Api")
@RequestMapping("/product_detail")
public class ProductDetailInStoreController {

    @Autowired
    private IProductInStoreDetailService iProductDetailService;

//    @ApiOperation(value = "添加定义")
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public ServiceResult add(@RequestBody ProductDetailInStore productDetail) throws Exception {
//        if (productDetail == null || StringUtils.isAnyBlank(productDetail.getPddefId(),productDetail.getPur()))
//            throw  new ParameterIllegalException("参数不合法");
//        CreateFieldFill.fill(productDetail);
//        iProductDetailService.save(productDetail);
//        return ServiceResult.success();
//    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServiceResult delete(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        iProductDetailService.removeById(id);
        return ServiceResult.success();
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delbatch", method = RequestMethod.DELETE)
    public ServiceResult delBatch(@RequestBody List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        iProductDetailService.removeByIds(ids);
        return ServiceResult.success();
    }

    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServiceResult findById(@PathVariable("id") Integer id) throws Exception {
        if (id == null)
            throw new ParameterIllegalException();
        return ServiceResult.success(iProductDetailService.getById(id));
    }

    @ApiOperation(value = "批量查询", notes = "批量查询")
    @RequestMapping(value = "/byids", method = RequestMethod.GET)
    public ServiceResult findByIds(@RequestParam(value = "ids")  List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0)
            throw new ParameterIllegalException();
        return ServiceResult.success(iProductDetailService.listByIds(ids));
    }

    @ApiOperation(value = "条件查询，分页", notes = "条件查询")
    @RequestMapping(value = "/bypage", method = RequestMethod.GET)
    public ServiceResult findByCondByPage(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "defId", required = false) Integer defId,
            @RequestParam(value = "purId", required = false) Integer purId,
            @RequestParam(value = "storeId", required = false) Integer storeId,
            @RequestParam(value = "orderId", required = false) Integer orderId,
            @RequestParam(value = "patterName", required = false) String patterName,
            @RequestParam(value = "purDateStartTime", required = false) Long purDateStartTime,
            @RequestParam(value = "purDateEndTime", required = false)Long purDateEndTime,
            @RequestParam(value = "orderDateStartTime", required = false) Long orderDateStartTime,
            @RequestParam(value = "orderDateEndTime", required = false) Long orderDateEndTime,
            @PageableDefault(value = 10, sort = {"update_date"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws Exception {
        return ServiceResult.success(iProductDetailService.findByCondPage(name, status, defId, purId, storeId, orderId,
                purDateStartTime, purDateEndTime,orderDateStartTime, orderDateEndTime, patterName,pageable));
    }

//    @ApiOperation(value = "条件查询")
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public ServiceResult findByCond(
//            @RequestParam(value = "name", required = false) String name
//    ) throws Exception {
//        QueryWrapper<ProductDetailInStore> queryWrapper = new QueryWrapper<>();
//        if (StringUtils.isNotBlank(name)) {
//            queryWrapper.eq(ProductDetailInStore.PROD_DEF_NAME, name);
//        }
//        SortProcessor.process(queryWrapper);
//        return ServiceResult.success(iProductDetailService.list(queryWrapper));
//    }

    @ApiOperation(value = "更新详情")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ServiceResult update(@RequestBody ProductDetailInStore productDetail) throws Exception {
        if (productDetail == null || productDetail.getId() == null)
            throw new ParameterIllegalException();
        iProductDetailService.updateById(productDetail);
        return ServiceResult.success();
    }
}
