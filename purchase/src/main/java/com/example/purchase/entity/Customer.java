package com.example.purchase.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Customer class
 * description: 客户
 *
 * @author huangyiran
 * @date 2022/9/6
 */

@Data
public class Customer {
    private String customerId;
    private String customerName;
    private String customerGender;
    private Integer customerAge;

    private String password;

}
