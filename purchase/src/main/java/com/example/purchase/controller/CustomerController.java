package com.example.purchase.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.example.purchase.dto.*;
import com.example.purchase.service.CustomerService;
import com.example.purchase.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.purchase.constant.Time.MY_TIME_FORMAT;
import static com.example.purchase.utils.Utils.*;

/**
 * CustomerController class
 * description: 客户系统controller层
 *
 * @author huangyiran
 * @date 2022/9/6
 */

@Slf4j
@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerService customerService;

    /**
     * 查询大额存单产品
     *
     * @return 产品信息列表VO
     */
    @RequestMapping("/listProducts")
    public JSONObject listProducts () {
        if (log.isInfoEnabled()) {
            log.info("purchaseProduct request");
        }
        //调用业务逻辑，获取产品信息DTO
        ArrayList<ProductDTO> listProductsDTOs = customerService.listProducts();
        //DTO转VO
        ArrayList<ProductVO> listProductsOutputVOs = new ArrayList<>();
        for (ProductDTO listProductsDTO : listProductsDTOs) {
            ProductVO listProductsOutputVO = new ProductVO();
            listProductsOutputVO.setProductId(listProductsDTO.getProductId());
            listProductsOutputVO.setProductName(listProductsDTO.getProductName());
            listProductsOutputVO.setTotalAmount(listProductsDTO.getTotalAmount());
            listProductsOutputVO.setAmountLowerLimit(listProductsDTO.getAmountLowerLimit());
            listProductsOutputVO.setAmountUpperLimit(listProductsDTO.getAmountUpperLimit());
            //将开始购买/购买截止时间截断，保留日期
            listProductsOutputVO.setStartSellDate(timeToDate(listProductsDTO.getStartSellDate()));
            listProductsOutputVO.setStopSellDate(timeToDate(listProductsDTO.getStopSellDate()));
            listProductsOutputVO.setYearInterestRate(listProductsDTO.getYearInterestRate());
            listProductsOutputVO.setAmountLeft(listProductsDTO.getAmountLeft());

            listProductsOutputVOs.add(listProductsOutputVO);
        }

        JSONObject ret = new JSONObject();
        ret.put("data", (JSONArray) JSON.toJSON(listProductsOutputVOs));
        return ret;
    }

    /**
     * 购买产品请求
     *
     * @param purchaseProductInputVO 购买产品的用户信息、产品信息VO
     * @return 交易状态VO（成功/失败+错误信息）
     */
    @RequestMapping("/purchaseProduct")
    public JSONObject purchaseProduct (@RequestBody @Valid PurchaseProductInputVO purchaseProductInputVO) {
        if (log.isInfoEnabled()) {
            log.info("purchaseProduct request: {}", purchaseProductInputVO);
        }
        //VO转DTO
        PurchaseDTO purchaseProductInputDTO = new PurchaseDTO();
        purchaseProductInputDTO.setCustomerId(purchaseProductInputVO.getCustomerId());
        purchaseProductInputDTO.setProductId(purchaseProductInputVO.getProductId());
        purchaseProductInputDTO.setPurchaseAmount(purchaseProductInputVO.getPurchaseAmount());
        //购买时间设为当前时间
        purchaseProductInputDTO.setPurchaseTime(new SimpleDateFormat(MY_TIME_FORMAT).format(new Date()));
        //DTO传给service层，返回DTO
        StatusMessageDTO purchaseProductOutputDTO = customerService.purchaseProduct(purchaseProductInputDTO);
        //DTO转VO
        StatusMessageVO purchaseProductOutputVO = new StatusMessageVO();
        purchaseProductOutputVO.setStatus(purchaseProductOutputDTO.getStatus());
        purchaseProductOutputVO.setMessage(purchaseProductOutputDTO.getMessage());
        return (JSONObject) JSON.toJSON(purchaseProductOutputVO);
    }

    /**
     * 查询购买记录请求
     *
     * @param listMyPurchasesInputVO 用户id VO
     * @return 该用户的购买记录VO,
     */
    @RequestMapping("/listMyPurchases")
    public JSONObject listMyPurchases (@RequestBody @Valid CustomerIdVO listMyPurchasesInputVO) {
        if (log.isInfoEnabled()) {
            log.info("listMyPurchases request: {}", listMyPurchasesInputVO);
        }
        //VO转DTO
        CustomerIdDTO listMyPurchasesInputDTO = new CustomerIdDTO();
        listMyPurchasesInputDTO.setCustomerId(listMyPurchasesInputVO.getCustomerId());
        //DTO传入service层，返回DTO
        ArrayList<ListMyPurchasesOutputDTO> listMyPurchasesOutputDTOs = customerService.listMyPurchases(listMyPurchasesInputDTO);
        //DTO转VO
        ArrayList<ListMyPurchasesOutputVO> listMyPurchasesOutputVOs = new ArrayList<>();
        for (ListMyPurchasesOutputDTO listMyPurchasesOutputDTO : listMyPurchasesOutputDTOs) {
            ListMyPurchasesOutputVO listMyPurchasesOutputVO = new ListMyPurchasesOutputVO();
            listMyPurchasesOutputVO.setProductId(listMyPurchasesOutputDTO.getProductId());
            listMyPurchasesOutputVO.setProductName(listMyPurchasesOutputDTO.getProductName());
            listMyPurchasesOutputVO.setPurchaseTime(listMyPurchasesOutputDTO.getPurchaseTime());
            listMyPurchasesOutputVO.setPurchaseAmount(listMyPurchasesOutputDTO.getPurchaseAmount());
            listMyPurchasesOutputVOs.add(listMyPurchasesOutputVO);
        }

        JSONObject ret = new JSONObject();
        ret.put("data", (JSONArray) JSON.toJSON(listMyPurchasesOutputVOs));
        return ret;
    }

    @RequestMapping("/login")
    public JSONObject login (@RequestBody CustomerLoginVO customerLoginVO) {
        CustomerLoginDTO customerLoginDTO = new CustomerLoginDTO();
        customerLoginDTO.setCustomerId(customerLoginVO.getCustomerId());
        customerLoginDTO.setPassword(customerLoginVO.getPassword());
        StatusMessageDTO statusMessageDTO = customerService.login(customerLoginDTO);
        StatusMessageVO loginOutputVO = new StatusMessageVO();
        loginOutputVO.setStatus(statusMessageDTO.getStatus());
        loginOutputVO.setMessage(statusMessageDTO.getMessage());
        return (JSONObject) JSON.toJSON(loginOutputVO);
    }

}
