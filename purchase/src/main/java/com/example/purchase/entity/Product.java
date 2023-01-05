package com.example.purchase.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Product class
 * description: 大额存单产品
 *
 * @author huangyiran
 * @date 2022/9/6
 */

@Data
public class Product {
    private String productId;
    private String productName;
    private BigDecimal totalAmount;
    private BigDecimal amountLowerLimit;
    private BigDecimal amountUpperLimit;
    private String startSellDate;
    private String stopSellDate;
    private Double yearInterestRate;

    private BigDecimal amountLeft;
}
