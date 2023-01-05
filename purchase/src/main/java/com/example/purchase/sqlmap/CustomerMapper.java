package com.example.purchase.sqlmap;

import com.example.purchase.entity.Customer;
import com.example.purchase.entity.Product;
import com.example.purchase.entity.Purchase;
import com.example.purchase.entity.PurchaseProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * CustomerMapper class
 * description: 客户系统mapper
 *
 * @author huangyiran
 * @date 2022/9/6
 */
@Mapper
public interface CustomerMapper {


    /**
     * 查询Product表中所有数据
     *
     * @return Product表中所有数据（产品信息）列表
     */
    public ArrayList<Product> listProducts ();

    /**
     * 查询指定ID的产品信息
     *
     * @param productId 产品id
     * @return 该id对应的产品信息
     */
    public Product getProduct (String productId);

    /**
     * 查询指定用户已经购买指定产品的总金额（用于计算用户能否继续购买此产品）
     *
     * @param purchaseInfoDO 用户id、产品id
     * @return 总金额
     */
    public Purchase getCustomerPurchaseTotalAmountByProduct (Purchase purchaseInfoDO);

    /**
     * 将指定产品的”剩余金额“字段减去用户购买的数值（不能小于0）
     *
     * @param purchaseProductDO 被购买的产品id、金额
     */
    void updateProductAmountLeft (Purchase purchaseProductDO);

    /**
     * 新增一条购买记录
     *
     * @param insertPurchaseDO 购买记录
     */
    void insertPurchase (Purchase insertPurchaseDO);

    /**
     * 查询指定用户的购买记录
     *
     * @param listMyPurchasesInputDO 用户id
     * @return 该用户的购买记录列表
     */
    ArrayList<PurchaseProduct> listPurchasesByCustomerId (Customer listMyPurchasesInputDO);

    Customer login (Customer loginInputDO);
}
