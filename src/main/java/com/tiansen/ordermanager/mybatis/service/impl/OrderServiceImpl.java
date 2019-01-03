package com.tiansen.ordermanager.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiansen.ordermanager.common.code.ServiceResultCode;
import com.tiansen.ordermanager.common.util.PoiUtils;
import com.tiansen.ordermanager.exception.BusinessException;
import com.tiansen.ordermanager.exception.ParameterIllegalException;
import com.tiansen.ordermanager.mybatis.entity.*;
import com.tiansen.ordermanager.mybatis.fill.CreateFieldFill;
import com.tiansen.ordermanager.mybatis.mapper.*;
import com.tiansen.ordermanager.mybatis.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rylai
 * @since 2018-12-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private static final int DATE_INDEX = 0;
    private static final int COMB_NAME_INDEX = 1;
    private static final int COMB_NUM_INDEX = 2;
    private static final int COMB_DETAIL_INDEX = 3;
    private static final int PROD_SPEC_INDEX = 4;
    private static final int PROD_CODE_INDEX = 5;
    private static final int BOX_NUM_INDEX = 6;
    private static final int PLATFORM_NAME_INDEX = 7;

    /**
     * 快递
     */
    private static final int EXP_DISCRIPTION_INDEX = 8;
    private static final int EXP_RECIPIENT_INDEX = 9;
    private static final int EXP_MOBILE_INDEX = 10;
    private static final int EXP_ADDRESS_INDEX = 11;
    private static final int EXP_COMPANY_INDEX = 12;
    private static final int EXP_ORDER_CODE_INDEX = 13;
    private static final int EXP_REMARK_INDEX = 14;

    @Autowired
    private CombinationMapper combinationMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProposerMapper proposerMapper;

    @Autowired
    private ExpressMapper expressMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void addModel(MultipartFile excelFile) throws Exception {
        InputStream in = excelFile.getInputStream();
        Map<String, List<List<Object>>> sheetsMapList = PoiUtils.getSheetsMapList(in, excelFile.getOriginalFilename(), 0, 1, 2);
        List<List<Object>> list = sheetsMapList.get("sheet0");
        List<Object> header = list.get(0);
        //验证Excel格式
        if (header.size() != 8
                || header.size() != 15
                || header.get(0).equals("日期")) {
            throw new BusinessException(ServiceResultCode.INPUT_EXCEL_ERRO.getCode());
        }

        //        0:日期
        //        2:组数
        //        6:盒数
        //        7:申请人
        //
        //#------- 组合 ----------
        //        1:组合名称
        //#----------------------
        //
        //#------- 产品 ---------
        //        3:明细产品
        //        4:规格
        //        5:货号
        //#---------------------
        //
        //
        //#------- 快递 ---------
        //        8:内容
        //        9:姓名
        //        10:电话
        //        11:地址
        //        12:运输公司
        //        13:快递单号
        //        14:备注
        //#---------------------

        boolean hasExpressInfo = false;
        if (header.size() == 15 && header.get(0).equals("日期") && header.get(14).equals("备注")) {
            // 有快递信息
            hasExpressInfo = true;
        }

        List<Combination> combinations = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<ProductDetail> productDetails = new ArrayList<>();
        List<ProductDefinition> productDefinitions = new ArrayList<>();
        List<Express> expresses = new ArrayList<>();
        for(int i=1;i<list.size();i++){
            Order order = new Order();
            int j = i + 1;
            List<Object> row = list.get(i);
            order.setOdDate((String) row.get(DATE_INDEX));
            CreateFieldFill.fill(order);
            String prodSpec = row.get(PROD_SPEC_INDEX) == null ? null : String.valueOf(row.get(PROD_SPEC_INDEX));// 产品规格
            String combProdDetails = row.get(COMB_DETAIL_INDEX) == null ? null : String.valueOf(row.get(COMB_DETAIL_INDEX));// 产品明细
            String combName = row.get(COMB_NAME_INDEX) == null ? null : String.valueOf(row.get(COMB_NAME_INDEX)); // 组合名称
            String combNum = row.get(COMB_NUM_INDEX) == null ? null : String.valueOf(row.get(COMB_NUM_INDEX)); // 组合数
            String boxNum = row.get(BOX_NUM_INDEX) == null ? null : String.valueOf(row.get(BOX_NUM_INDEX)); // 总盒数
            String propName = row.get(PLATFORM_NAME_INDEX) == null ? null : String.valueOf(row.get(PLATFORM_NAME_INDEX)); // 平台名称
            // ------------------------------ 开始处理 产品 ------------------------------------------
            if (StringUtils.isBlank(combProdDetails)) {
                throw new ParameterIllegalException("第"+j+"行 明细产品不能为空");
            }
            List<String> prodNames = Arrays.asList(combProdDetails.split("\n"));
//            List<Product> prods = productDetailMapper.selectList(
//                    new QueryWrapper<Product>()
//                            .in(Product.PROD_NAME, prodNames)
//            );
//
//            if (prods == null || prods.size() != prodNames.size()) {
//                throw new ParameterIllegalException("第"+j+"行 明细产品 有不存在产品名称");
//            }
//
//            List<Integer> prodIds = new ArrayList<>();
//            for (Product product : prods) {
//                prodIds.add(product.getId());
//            }

            // ------------------------------ 开始处理 组合 ------------------------------------------


            if (StringUtils.isNotBlank(combName)) {
                if (!StringUtils.isNumeric(combNum)) {
                    throw new ParameterIllegalException("第"+j+"行 组合数量 参数有误");
                }
                if (!StringUtils.isNumeric(boxNum)) {
                    throw new ParameterIllegalException("第"+j+"行 盒数 参数有误");
                }
                // 每组盒数
                order.setOdBoxNum(Integer.parseInt(boxNum));
                float boxNumPerComb = Float.parseFloat(boxNum) / Float.parseFloat(combName);
                if (boxNumPerComb != (int) boxNumPerComb) {
                    throw  new ParameterIllegalException("第"+j+"行 总盒数和组合数不匹配");
                }

                // 处理组合信息
                Combination comb = combinationMapper.selectOne(
                        new QueryWrapper<Combination>()
                                .eq(Combination.COMB_NAME, combName));
                if (comb != null) {
//                    order.setOdCombId(comb.getId());
                } else {
                    Combination combination = new Combination();
                    combination.setCombName(combName);
//                            .setCombBoxNum((int) boxNumPerComb)
//                            .setCombProdIds(prodIds.toArray());
                    combinations.add(combination);
                }
            } else {
                // 不同产品组合装
                // 递归去拿组合名称 并动态插入
                Integer parentRowLine = lookupCombOrder(list, new Integer(i));
                if (parentRowLine == null)
                    throw new ParameterIllegalException("第"+j+"行多产品查找组合名称错误：空");
                orders.get(parentRowLine);

            }
            // ---------------------------------- 处理平台 ------------------------------------------
            if (StringUtils.isBlank(propName)) {
                throw new ParameterIllegalException("第"+j+"行 平台名称不能为空");
            }
            Proposer platform = proposerMapper.selectOne(
                    new QueryWrapper<Proposer>()
                            .eq(Proposer.PROP_NAME, propName)
                            .eq(Proposer.PROP_NAME, propName)
            );
            if (platform == null) {
                throw new ParameterIllegalException("第"+j+"行 平台不存在");
            }
            // 平台id
            order.setPropId(platform.getId());

            // ---------------------------------- 处理快递 ------------------------------------------
            if (hasExpressInfo) {
                // 查询order
                Map<String, Object> map = new HashMap<>();
//                if (ma)


                String address = row.get(EXP_ADDRESS_INDEX) == null ? null :  String.valueOf(row.get(EXP_ADDRESS_INDEX)); // 收件地址
                String company = row.get(EXP_COMPANY_INDEX) == null ? null : String.valueOf(row.get(EXP_COMPANY_INDEX)); // 快递公司
                String discripiton = row.get(EXP_DISCRIPTION_INDEX) == null ? null : String.valueOf(row.get(EXP_DISCRIPTION_INDEX)); // 描述
                String mobile = row.get(EXP_MOBILE_INDEX) == null ? null : String.valueOf(row.get(EXP_MOBILE_INDEX)); // 手机号
                String recipent = row.get(EXP_RECIPIENT_INDEX) == null ? null : String.valueOf(row.get(EXP_RECIPIENT_INDEX)); // 收件人
                String remark = row.get(EXP_REMARK_INDEX) == null ? null : String.valueOf(row.get(EXP_REMARK_INDEX)); // 备注
                String orderCode = String.valueOf(row.get(EXP_ORDER_CODE_INDEX)); // 单号
                if (StringUtils.isBlank(address))
                    throw new ParameterIllegalException("第"+j+"行 地址不能为空");
                if (StringUtils.isBlank(company))
                    throw new ParameterIllegalException("第"+j+"行 运输公司不能为空");
                if (StringUtils.isBlank(mobile))
                    throw new ParameterIllegalException("第"+j+"行 收件人电话不能为空");
                if (StringUtils.isBlank(recipent))
                    throw new ParameterIllegalException("第"+j+"行 收件人姓名不能为空");
                if (StringUtils.isBlank(orderCode))
                    throw new ParameterIllegalException("第"+j+"行 快递单号不能为空");

                expressMapper.selectOne(
                        new QueryWrapper<Express>()
                                .eq(Express.EXP_ORDER_CODE, orderCode)
                );
            }

        }



//        if(header.size()<8||!String.valueOf(header.get(0)).equals("资产唯一编号")){
////            baseRsp.setState(CommonStatus.INPUT_EXCEL_ERRO);
////            return ResponseEntity.ok(baseRsp);
//            throw new BusinessException(ServiceResultCode.INPUT_EXCEL_ERRO.getCode());
//        }
//        // 订单信息
//        List<Order> orders = new ArrayList<>();
//
//
//
//        List<JSONObject> erroMessage = new ArrayList<>();//添加错误信息
//        for(int i=2;i<list.size();i++){
//            List<Object> ob= list.get(i);
//            int j = i+1;
//            int x =0;
//            Product product = new Product();
//            JSONObject jsonObject = new JSONObject();
//
//            Map<String,Object> map = new HashMap<>();
//            if(StringUtils.isBlank(String.valueOf(ob.get(0)))){
//                jsonObject.put("第"+j+"行唯一编号","资产唯一编号为空");
//                x=1;
//            }
//            if(StringUtils.isNotBlank(String.valueOf(ob.get(5)))){
//                map.put(Employee.EMP_CODE,String.valueOf(ob.get(5)));
//                Employee employee = employeeDao.selectOnlyOneByMapCondition(map);
//                if(null==employee){
//                    jsonObject.put("第"+j+"行员工","员工不存在");
//                    x=1;
//                }else{
//                    product.setEmp_info_id(employee.getId());
//                }
//            }
//
//            map.put(Assetstype.TYPECODE,String.valueOf(ob.get(4)));
//            Assetstype assetstype =assetstypeDao.selectOnlyOneByMapCondition(map);
//            if(null==assetstype){
//                jsonObject.put("第"+j+"行所属类型","类型不存在");
//                x=1;
//            }else{
//                product.setType_info_id(assetstype.getId());
//            }
//
//            if(StringUtils.isNotBlank(String.valueOf(ob.get(7)))){
//                map.put(Assetsstore.SOTRE_CODE,String.valueOf(ob.get(7)));
//                Assetsstore assetsstore = assetsstoreDao.selectOnlyOneByMapCondition(map);
//                if(null==assetsstore){
//                    jsonObject.put("第"+j+"行所属仓库","仓库不存在");
//                    x=1;
//                }else{
//                    product.setStore_info_id(assetsstore.getId());
//                }
//            }
//
//            map.put(Product.BASE_UNIQUE,String.valueOf(ob.get(0)));
//            Product product1 = productDao.selectOnlyOneByMapCondition(map);
//            if(null!=product1){
//                jsonObject.put("第"+j+"行编号","资产唯一编号重复");
//                x=1;
//            }
//            if(x==0) {
//                product.setBase_unique(String.valueOf(ob.get(0)));
//                product.setBase_brand(String.valueOf(ob.get(1)));
//                product.setBase_model(String.valueOf(ob.get(2)));
//                if (StringUtils.isNotBlank(String.valueOf(ob.get(3)))) {
//                    product.setExpiration_time(Timestamp.valueOf(String.valueOf(ob.get(3))));
//                }
//
//                product.setBase_status(String.valueOf(ob.get(6)));
//                Long maxSeq = productDao.findMaxSeqValue(Product.BASE_SEQ);
//                if (maxSeq == null)
//                    maxSeq = 0l;
//                long currentSeq = maxSeq + 1;
//                product.setBase_seq(currentSeq);
//
//                // base_epc_code
//                String epc =HexAscByteUtil.str2HexStr(product.getBase_unique());
//                product.setBase_epc_code(epc);
//                product.setLastop_date(RylaiDate.getCurrTimstamp());
//                product.setAppend_info(new Object());
//                productDao.insert(product);
//            }
//            if(!jsonObject.isEmpty()){
//                erroMessage.add(jsonObject);
//            }
//        }
    }

    /**
     * 递归查询
     * @param list
     * @param i
     * @return
     */
    private Integer lookupCombOrder(List<List<Object>> list, int i) {
        if (i < 2)
            return null;

        if (list.get(--i) == null)
            return null;

        String combName = list.get(i).get(COMB_NAME_INDEX) == null  ? null : String.valueOf(list.get(i).get(COMB_NAME_INDEX));
        if (StringUtils.isBlank(combName)) {
            lookupCombOrder(list, i);
        } else {
            return i;
        }
        return null;
    }
}
