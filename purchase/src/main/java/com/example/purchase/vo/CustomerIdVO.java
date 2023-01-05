package com.example.purchase.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * CustomerIdVO class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/6
 */
@Data
public class CustomerIdVO {

    @NotNull(message = "客户id不能为空")
    @Size(min = 1, message = "客户id不能为空")
    private String customerId;


}
