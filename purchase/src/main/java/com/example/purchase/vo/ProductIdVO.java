package com.example.purchase.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ProductIdVO class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/12
 */

@Data
public class ProductIdVO {

    @NotNull(message = "产品id不能为空")
    @Size(min = 1, message = "产品id不能为空")
    private String productId;
}
