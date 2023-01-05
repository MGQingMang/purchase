package com.example.purchase.vo;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * ProductVO class
 * description: TODO
 *
 * @author huangyiran
 * @date 2022/9/6
 */
@Data
public class ProductVO {
    @NotNull(message = "产品id不能为空")
    @Size(min = 1, message = "产品id不能为空")
    private String productId;

    @NotNull(message = "产品名称不能为空")
    @Size(min = 1, message = "产品名称不能为空")
    private String productName;

    @NotNull(message = "总金额不能为空")
    @Min(value = 0, message = "总金额必须大于0")
    private BigDecimal totalAmount;

    @NotNull(message = "金额下限不能为空")
    @Min(value = 0, message = "金额下限必须大于0")
    private BigDecimal amountLowerLimit;

    @NotNull(message = "金额上限不能为空")
    @Min(value = 0, message = "金额上限必须大于0")
    private BigDecimal amountUpperLimit;

    @NotNull(message = "开始售卖日期不能为空")
    @Size(min = 1, message = "开始售卖日期不能为空")
    private String startSellDate;

    @NotNull(message = "售卖截止日期不能为空")
    @Size(min = 1, message = "售卖截止日期不能为空")
    private String stopSellDate;

    @NotNull(message = "年化利率不能为空")
    @Min(value = 0, message = "年化利率需要大于0")
    private Double yearInterestRate;

    @NotNull(message = "剩余金额不能为空")
    @Min(value = 0, message = "剩余金额需要大于0")
    private BigDecimal amountLeft;

}
