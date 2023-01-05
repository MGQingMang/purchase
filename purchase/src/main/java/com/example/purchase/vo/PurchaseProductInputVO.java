package com.example.purchase.vo;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * PurchaseProductInputVO class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/6
 */
@Data
public class PurchaseProductInputVO {

    @NotNull(message = "客户id不能为空")
    @Size(min = 1, message = "客户id不能为空")
    private String customerId;

    @NotNull(message = "产品id不能为空")
    @Size(min = 1, message = "产品id不能为空")
    private String productId;

    @NotNull(message = "购买金额不能为空")
    @DecimalMin(value = "0", message = "购买金额必须是大于0的数字")
    private BigDecimal purchaseAmount;
}
