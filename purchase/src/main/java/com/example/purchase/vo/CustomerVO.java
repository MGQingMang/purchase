package com.example.purchase.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * CustomerVO class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/12
 */

@Data
public class CustomerVO  {

    @NotNull(message = "客户id不能为空")
    @Size(min = 1, message = "客户id不能为空")
    private String customerId;

    @NotNull(message = "客户姓名不能为空")
    @Size(min = 1, message = "客户姓名不能为空")
    private String customerName;

    @NotNull(message = "客户性别不能为空")
    @Size(min = 1, message = "客户性别不能为空")
    private String customerGender;

    @NotNull(message = "客户年龄不能为空")
    @Min(value = 0, message = "年龄必须大于0")
    private Integer customerAge;
}
