package com.example.purchase.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ListMyPurchasesOutputDTO class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/6
 */
@Data
public class ListMyPurchasesOutputDTO {
    private String customerId;
    private String productId;
    private String productName;
    private String purchaseTime;
    private BigDecimal purchaseAmount;
}
