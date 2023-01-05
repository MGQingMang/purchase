package com.example.purchase.constant;

/**
 * Message class
 * description: 交易信息相关常量
 *
 * @author huangyiran
 * @date 2022/9/10
 */
public class Message {
    public static final String NOT_IN_PURCHASE_TIME = "购买失败！不在购买时间内";

    public static final String AMOUNT_LOWER_THAN_LIMIT = "购买失败！购买金额需要大于规定的下限";

    public static final String AMOUNT_HIGHER_THAN_LEFT = "购买失败！购买金额需要小于产品剩余金额";

    public static final String AMOUNT_HIGHER_THAN_LIMIT = "购买失败！您购买此产品的金额不能超出规定的上限，您还可购买此产品的金额：";

    public static final String DUPLICATE_TRADE = "购买失败！请勿重复操作";

    public static final String PURCHASE_SUCCESS = "购买成功！";

    public static final String INSERT_PRODUCT_FAILURE = "该产品已存在，请使用更新产品功能";

    public static final String DELETE_PRODUCT_FAILURE = "产品不存在";

    public static final String UPDATE_PRODUCT_FAILURE = "产品不存在";

    public static final String PRODUCT_SUCCESS = "产品信息修改成功";

    public static final String INSERT_CUSTOMER_FAILURE = "该用户已存在，请使用更新用户功能";

    public static final String DELETE_CUSTOMER_FAILURE = "用户不存在";

    public static final String UPDATE_CUSTOMER_FAILURE = "用户不存在";

    public static final String CUSTOMER_SUCCESS = "用户信息修改成功";

    public static final String LOGIN_SUCCESS = "登录成功";

    public static final String LOGIN_FAILURE = "登录失败";

    public static final String PRODUCT_NOT_EXIST = "产品不存在";
}
