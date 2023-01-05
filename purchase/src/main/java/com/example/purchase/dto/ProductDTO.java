package com.example.purchase.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ProductDTO class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/6
 */
@Data
public class ProductDTO {
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
