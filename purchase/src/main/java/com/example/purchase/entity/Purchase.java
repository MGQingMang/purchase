package com.example.purchase.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Purchase class
 * description: 购买记录
 *
 * @author huangyiran
 * @date 2022/9/6
 */

@Data
public class Purchase {
    private String customerId;
    private String productId;
    private String purchaseTime;
    private BigDecimal purchaseAmount;
}
