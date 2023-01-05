package com.example.purchase.service;

import com.example.purchase.dto.*;
import com.example.purchase.entity.Customer;
import com.example.purchase.entity.Product;
import com.example.purchase.entity.Purchase;
import com.example.purchase.entity.PurchaseProduct;
import com.example.purchase.sqlmap.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.example.purchase.constant.Message.*;
import static com.example.purchase.constant.Status.*;

/**
 * CustomerService class
 * description: 客户系统业务处理
 *
 * @author huangyiran
 * @date 2022/9/6
 */

@Slf4j
@Service
public class CustomerService {

    @Autowired
    CustomerMapper customerMapper;


    /**
     * 查询大额存单产品
     *
     * @return 产品信息列表DTOs
     */
    public ArrayList<ProductDTO> listProducts () {
        //调用mapper层，获取DO
        ArrayList<Product> listProductsOutputDOs = customerMapper.listProducts();
        //DO转DTO
        ArrayList<ProductDTO> listProductsOutputDTOs = new ArrayList<>();
        for (Product listProductOutputDO : listProductsOutputDOs) {
            ProductDTO listProductsDTO = new ProductDTO();
            listProductsDTO.setProductId(listProductOutputDO.getProductId());
            listProductsDTO.setProductName(listProductOutputDO.getProductName());
            listProductsDTO.setTotalAmount(listProductOutputDO.getTotalAmount());
            listProductsDTO.setAmountLowerLimit(listProductOutputDO.getAmountLowerLimit());
            listProductsDTO.setAmountUpperLimit(listProductOutputDO.getAmountUpperLimit());
            listProductsDTO.setStartSellDate(listProductOutputDO.getStartSellDate());
            listProductsDTO.setStopSellDate(listProductOutputDO.getStopSellDate());
            listProductsDTO.setYearInterestRate(listProductOutputDO.getYearInterestRate());
            listProductsDTO.setAmountLeft(listProductOutputDO.getAmountLeft());

            listProductsOutputDTOs.add(listProductsDTO);
        }
        return listProductsOutputDTOs;
    }

    /**
     * 购买大额存单产品
     *
     * @param purchaseProductInputDTO 购买产品输入的DTO
     * @return 购买成功/失败及其信息的DTO
     */
    public StatusMessageDTO purchaseProduct (PurchaseDTO purchaseProductInputDTO) {
        //用户想要购买的金额
        BigDecimal purchaseProductInputDTOPurchaseAmount = purchaseProductInputDTO.getPurchaseAmount();
        StatusMessageDTO failureDTO = new StatusMessageDTO();
        failureDTO.setStatus(STATUS_FAILURE);
        //先从数据库中获取此产品信息，然后依次判断是否符合购买条件
        Product getProductOutputDO = customerMapper.getProduct(purchaseProductInputDTO.getProductId());
        if (null == getProductOutputDO) {
            failureDTO.setMessage(PRODUCT_NOT_EXIST);
            if (log.isInfoEnabled()) {
                log.info("purchaseProduct: failure: 产品不存在");
            }
            return failureDTO;
        }
        //条件1.判断时间是否在开始购买时间到购买截止时间内
        if (purchaseProductInputDTO.getPurchaseTime().compareTo(getProductOutputDO.getStopSellDate()) > 0 ||
                purchaseProductInputDTO.getPurchaseTime().compareTo(getProductOutputDO.getStartSellDate()) < 0) {
            failureDTO.setMessage(NOT_IN_PURCHASE_TIME);
            if (log.isInfoEnabled()) {
                log.info("purchaseProduct: failure: 超出时间限制");
            }
            return failureDTO;
        }
        //条件2.判断购买金额是否大于等于下限
        if (purchaseProductInputDTOPurchaseAmount.compareTo(getProductOutputDO.getAmountLowerLimit()) < 0) {
            failureDTO.setMessage(AMOUNT_LOWER_THAN_LIMIT);
            if (log.isInfoEnabled()) {
                log.info("purchaseProduct: failure: 购买金额小于下限");
            }
            return failureDTO;
        }
        //条件3.判断购买金额是否小于等于剩余可售卖金额
        if (purchaseProductInputDTOPurchaseAmount.compareTo(getProductOutputDO.getAmountLeft()) > 0) {
            failureDTO.setMessage(AMOUNT_HIGHER_THAN_LEFT);
            if (log.isInfoEnabled()) {
                log.info("purchaseProduct: failure: 购买金额大于剩余金额");
            }
            return failureDTO;
        }
        //条件4.判断该用户购买该产品的总金额是否小于等于上限
        Purchase purchaseInfoDTO = new Purchase();
        purchaseInfoDTO.setCustomerId(purchaseProductInputDTO.getCustomerId());
        purchaseInfoDTO.setProductId(purchaseProductInputDTO.getProductId());
        //查询数据库，获取该用户已经购买该产品的金额
        Purchase customerPurchaseTotalAmountDO = customerMapper.getCustomerPurchaseTotalAmountByProduct(purchaseInfoDTO);
        //若查询结果为空，即该用户未购买该产品，则此项判断通过，用户可以购买
        //若查询结果非空，继续判断是否超过购买金额上限
        if (null != customerPurchaseTotalAmountDO) {
            //客户当前可购买金额 = 客户可购买金额上限 - 客户已购买金额
            BigDecimal customerAllowPurchaseAmount = getProductOutputDO.getAmountUpperLimit()
                    .subtract(customerPurchaseTotalAmountDO.getPurchaseAmount());
            //若此金额小于客户想要购买的金额，报错
            if (customerAllowPurchaseAmount.compareTo(purchaseProductInputDTOPurchaseAmount) < 0) {
                failureDTO.setMessage(AMOUNT_HIGHER_THAN_LIMIT + customerAllowPurchaseAmount);
                if (log.isInfoEnabled()) {
                    log.info("purchaseProduct: failure: 购买金额超过该用户上限");
                }
                return failureDTO;
            }
        }
        //以上判断均通过，即用户可以购买此产品
        //更新数据库，该产品剩余金额需要减掉客户本次购买的金额
        Purchase purchaseProductDO = new Purchase();
        purchaseProductDO.setProductId(purchaseProductInputDTO.getProductId());
        purchaseProductDO.setPurchaseAmount(purchaseProductInputDTOPurchaseAmount);
        try {
            customerMapper.updateProductAmountLeft(purchaseProductDO);
        } catch (Exception e) {
            //特殊情况：若此时因为其他并发的请求导致产品剩余金额不足（不满足条件3），则利用数据库的约束条件令此次购买失败
            failureDTO.setMessage(AMOUNT_HIGHER_THAN_LEFT);
            if (log.isInfoEnabled()) {
                log.info("purchaseProduct: failure: 剩余金额不足！！！");
            }
            return failureDTO;
        }
        //购买成功，将购买记录写入数据库
        Purchase insertPurchaseDO = new Purchase();
        insertPurchaseDO.setCustomerId(purchaseProductInputDTO.getCustomerId());
        insertPurchaseDO.setProductId(purchaseProductInputDTO.getProductId());
        insertPurchaseDO.setPurchaseTime(purchaseProductInputDTO.getPurchaseTime());
        insertPurchaseDO.setPurchaseAmount(purchaseProductInputDTOPurchaseAmount);
        //若在同一秒产生两次交易，判定为重复操作，利用数据库的主键约束令此次购买失败（代替幂等）（需要用事务）
        /*try {
            customerMapper.insertPurchase(insertPurchaseDO);
        } catch (DuplicateKeyException e) {
            failureDTO.setMessage(DUPLICATE_TRADE);
            if (log.isInfoEnabled()) {
                log.info("purchaseProduct: failure: 重复交易");
            }
            return failureDTO;
        }*/
        customerMapper.insertPurchase(insertPurchaseDO);

        StatusMessageDTO successDTO = new StatusMessageDTO();
        successDTO.setStatus(STATUS_SUCCESS);
        successDTO.setMessage(PURCHASE_SUCCESS);
        if (log.isInfoEnabled()) {
            log.info("purchaseProduct: success");
        }
        return successDTO;
    }

    /**
     * 查询该用户的购买记录
     *
     * @param listMyPurchasesInputDTO 用户idVO
     * @return 用户的购买记录VO
     */
    public ArrayList<ListMyPurchasesOutputDTO> listMyPurchases (CustomerIdDTO listMyPurchasesInputDTO) {
        //DTO转DO
        Customer listMyPurchasesInputDO = new Customer();
        listMyPurchasesInputDO.setCustomerId(listMyPurchasesInputDTO.getCustomerId());
        //根据客户id查询购买记录（两表连接）
        ArrayList<PurchaseProduct> listMyPurchasesOutputDOs = customerMapper.listPurchasesByCustomerId(listMyPurchasesInputDO);
        //DO转DTO
        ArrayList<ListMyPurchasesOutputDTO> listMyPurchasesOutputDTOs = new ArrayList<>();
        for (PurchaseProduct listMyPurchasesOutputDO : listMyPurchasesOutputDOs) {
            ListMyPurchasesOutputDTO listMyPurchasesOutputDTO = new ListMyPurchasesOutputDTO();
            listMyPurchasesOutputDTO.setProductId(listMyPurchasesOutputDO.getProductId());
            listMyPurchasesOutputDTO.setProductName(listMyPurchasesOutputDO.getProductName());
            listMyPurchasesOutputDTO.setPurchaseTime(listMyPurchasesOutputDO.getPurchaseTime());
            listMyPurchasesOutputDTO.setPurchaseAmount(listMyPurchasesOutputDO.getPurchaseAmount());
            listMyPurchasesOutputDTO.setCustomerId(listMyPurchasesInputDTO.getCustomerId());

            listMyPurchasesOutputDTOs.add(listMyPurchasesOutputDTO);
        }
        return listMyPurchasesOutputDTOs;
    }

    public StatusMessageDTO login (CustomerLoginDTO customerLoginDTO) {
        Customer loginInputDO = new Customer();
        loginInputDO.setCustomerId(customerLoginDTO.getCustomerId());
        loginInputDO.setPassword(customerLoginDTO.getPassword());
        Customer loginOutputDO = customerMapper.login(loginInputDO);
        StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
        //若账号密码不匹配
        if (null == loginOutputDO) {
            statusMessageDTO.setStatus(STATUS_FAILURE);
            statusMessageDTO.setMessage(LOGIN_FAILURE);
            return statusMessageDTO;
        }
        //若账号密码匹配
        statusMessageDTO.setStatus(STATUS_SUCCESS);
        statusMessageDTO.setMessage(LOGIN_SUCCESS);
        return statusMessageDTO;
    }
}
