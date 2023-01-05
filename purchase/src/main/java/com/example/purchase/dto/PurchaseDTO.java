package com.example.purchase.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * PurchaseDTO class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/6
 */
@Data
public class PurchaseDTO {
    private String customerId;

    private String productId;

    private String purchaseTime;

    private BigDecimal purchaseAmount;
}
