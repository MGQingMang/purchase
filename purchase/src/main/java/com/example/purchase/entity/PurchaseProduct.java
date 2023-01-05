package com.example.purchase.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * PurchaseProduct class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/11
 */

@Data
public class PurchaseProduct {
    private String productId;
    private String productName;
    private String purchaseTime;
    private BigDecimal purchaseAmount;
}
