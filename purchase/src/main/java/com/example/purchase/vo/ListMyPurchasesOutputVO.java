package com.example.purchase.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ListMyPurchasesOutputVO class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/6
 */
@Data
public class ListMyPurchasesOutputVO {
    private String productId;
    private String productName;
    private String purchaseTime;
    private BigDecimal purchaseAmount;

}
