package com.example.purchase.dto;

import lombok.Data;

/**
 * CustomerDTO class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/12
 */

@Data
public class CustomerDTO {
    private String customerId;
    private String customerName;
    private String customerGender;
    private Integer customerAge;
}
