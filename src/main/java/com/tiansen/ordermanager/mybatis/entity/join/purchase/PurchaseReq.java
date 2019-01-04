package com.tiansen.ordermanager.mybatis.entity.join.purchase;

import com.tiansen.ordermanager.mybatis.entity.Purchase;
import lombok.Data;

import java.util.List;
@Data
public class PurchaseReq extends Purchase {
   List<PurchaseInfo> purchaseDetailInfos;
}
