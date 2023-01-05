package com.example.purchase.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.purchase.dto.*;
import com.example.purchase.service.CustomerService;
import com.example.purchase.service.ManagerService;
import com.example.purchase.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

import static com.example.purchase.utils.Utils.*;

/**
 * ManagerController class
 * description: 管理员系统controller层
 *
 * @author huangyiran
 * @date 2022/9/8
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @RequestMapping("/product/listProducts")
    public JSONObject listProducts () {
        if(log.isInfoEnabled()){
            log.info("listProducts request");
        }
        //调用业务逻辑，获取产品信息DTO
        ArrayList<ProductDTO> listProductsDTOs = managerService.listProducts();
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

    @RequestMapping("/product/insertProduct")
    public JSONObject insertProduct (@RequestBody @Valid ProductVO insertProductInputVO) {
        if(log.isInfoEnabled()){
            log.info("insertProduct request: {}", insertProductInputVO);
        }
        //VO转DTO
        ProductDTO insertProductInputDTO = new ProductDTO();
        insertProductInputDTO.setProductId(insertProductInputVO.getProductId());
        insertProductInputDTO.setProductName(insertProductInputVO.getProductName());
        insertProductInputDTO.setTotalAmount(insertProductInputVO.getTotalAmount());
        insertProductInputDTO.setAmountLowerLimit(insertProductInputVO.getAmountLowerLimit());
        insertProductInputDTO.setAmountUpperLimit(insertProductInputVO.getAmountUpperLimit());
        //时间
        insertProductInputDTO.setStartSellDate(dateToTimeBegin(insertProductInputVO.getStartSellDate()));
        insertProductInputDTO.setStopSellDate(dateToTimeEnd(insertProductInputVO.getStopSellDate()));
        insertProductInputDTO.setYearInterestRate(insertProductInputVO.getYearInterestRate());
        //剩余可售卖金额等于总金额
        insertProductInputDTO.setAmountLeft(insertProductInputVO.getTotalAmount());
        //DTO传入service层，返回DTO
        StatusMessageDTO insertProductOutputDTO = managerService.insertProduct(insertProductInputDTO);
        //DTO转VO
        StatusMessageVO insertProductOutputVO = new StatusMessageVO();
        insertProductOutputVO.setStatus(insertProductOutputDTO.getStatus());
        insertProductOutputVO.setMessage(insertProductOutputDTO.getMessage());
        return (JSONObject) JSON.toJSON(insertProductOutputVO);
    }

    @RequestMapping("/product/deleteProduct")
    public JSONObject deleteProduct (@RequestBody @Valid ProductIdVO deleteProductInputVO) {
        if(log.isInfoEnabled()){
            log.info("deleteProduct request: {}", deleteProductInputVO);
        }
        //VO转DTO
        ProductIdDTO deleteProductInputDTO = new ProductIdDTO();
        deleteProductInputDTO.setProductId(deleteProductInputVO.getProductId());
        //传给service层
        StatusMessageDTO deleteProductOutputDTO = managerService.deleteProduct(deleteProductInputDTO);
        //DTO转VO
        StatusMessageVO deleteProductOutputVO = new StatusMessageVO();
        deleteProductOutputVO.setStatus(deleteProductOutputDTO.getStatus());
        deleteProductOutputVO.setMessage(deleteProductOutputDTO.getMessage());
        return (JSONObject) JSON.toJSON(deleteProductOutputVO);
    }

    @RequestMapping("/product/updateProduct")
    public JSONObject updateProduct (@RequestBody @Valid ProductVO updateProductInputVO) {
        if(log.isInfoEnabled()){
            log.info("updateProduct request: {}", updateProductInputVO);
        }
        //VO转DTO
        ProductDTO updateProductInputDTO = new ProductDTO();
        updateProductInputDTO.setProductId(updateProductInputVO.getProductId());
        updateProductInputDTO.setProductName(updateProductInputVO.getProductName());
        updateProductInputDTO.setTotalAmount(updateProductInputVO.getTotalAmount());
        updateProductInputDTO.setAmountLowerLimit(updateProductInputVO.getAmountLowerLimit());
        updateProductInputDTO.setAmountUpperLimit(updateProductInputVO.getAmountUpperLimit());
        updateProductInputDTO.setStartSellDate(dateToTimeBegin(updateProductInputVO.getStartSellDate()));
        updateProductInputDTO.setStopSellDate(dateToTimeEnd(updateProductInputVO.getStopSellDate()));
        updateProductInputDTO.setYearInterestRate(updateProductInputVO.getYearInterestRate());
        updateProductInputDTO.setAmountLeft(updateProductInputVO.getAmountLeft());
        //DTO传入service层，返回DTO
        StatusMessageDTO updateProductOutputDTO = managerService.updateProduct(updateProductInputDTO);
        //DTO转VO
        StatusMessageVO updateProductOutputVO = new StatusMessageVO();
        updateProductOutputVO.setStatus(updateProductOutputDTO.getStatus());
        updateProductOutputVO.setMessage(updateProductOutputDTO.getMessage());
        return (JSONObject) JSON.toJSON(updateProductOutputVO);
    }

    @RequestMapping("/customer/listCustomers")
    public JSONObject listCustomers () {
        if(log.isInfoEnabled()){
            log.info("listCustomers request");
        }
        ArrayList<CustomerDTO> listCustomersOutputDTOs = managerService.listCustomers();
        ArrayList<CustomerVO> listCustomersOutputVOs = new ArrayList<>();
        for (CustomerDTO listCustomerDTO : listCustomersOutputDTOs) {
            CustomerVO listCustomerVO = new CustomerVO();
            listCustomerVO.setCustomerId(listCustomerDTO.getCustomerId());
            listCustomerVO.setCustomerName(listCustomerDTO.getCustomerName());
            listCustomerVO.setCustomerGender(listCustomerDTO.getCustomerGender());
            listCustomerVO.setCustomerAge(listCustomerDTO.getCustomerAge());
            listCustomersOutputVOs.add(listCustomerVO);
        }
        JSONObject ret = new JSONObject();
        ret.put("data", (JSONArray) JSON.toJSON(listCustomersOutputVOs));
        return ret;
    }

    @RequestMapping("/customer/insertCustomer")
    public JSONObject insertCustomer (@RequestBody @Valid CustomerVO insertCustomerInputVO) {
        if(log.isInfoEnabled()){
            log.info("insertCustomer request: {}", insertCustomerInputVO);
        }
        //VO转DTO
        CustomerDTO insertCustomerInputDTO = new CustomerDTO();
        insertCustomerInputDTO.setCustomerId(insertCustomerInputVO.getCustomerId());
        insertCustomerInputDTO.setCustomerName(insertCustomerInputVO.getCustomerName());
        insertCustomerInputDTO.setCustomerGender(insertCustomerInputVO.getCustomerGender());
        insertCustomerInputDTO.setCustomerAge(insertCustomerInputVO.getCustomerAge());
        //DTO传入service层，返回DTO
        StatusMessageDTO insertCustomerOutputDTO = managerService.insertCustomer(insertCustomerInputDTO);
        //DTO转VO
        StatusMessageVO insertProductOutputVO = new StatusMessageVO();
        insertProductOutputVO.setStatus(insertCustomerOutputDTO.getStatus());
        insertProductOutputVO.setMessage(insertCustomerOutputDTO.getMessage());
        return (JSONObject) JSON.toJSON(insertProductOutputVO);
    }

    @RequestMapping("/customer/deleteCustomer")
    public JSONObject deleteCustomer (@RequestBody @Valid CustomerIdVO deleteCustomerInputVO) {
        if(log.isInfoEnabled()){
            log.info("deleteCustomer request: {}", deleteCustomerInputVO);
        }
        //VO转DTO
        CustomerIdDTO deleteCustomerInputDTO = new CustomerIdDTO();
        deleteCustomerInputDTO.setCustomerId(deleteCustomerInputVO.getCustomerId());
        //DTO传入service层，返回DTO
        StatusMessageDTO deleteCustomerOutputDTO = managerService.deleteCustomer(deleteCustomerInputDTO);
        //DTO转VO
        StatusMessageVO deleteCustomerOutputVO = new StatusMessageVO();
        deleteCustomerOutputVO.setStatus(deleteCustomerOutputDTO.getStatus());
        deleteCustomerOutputVO.setMessage(deleteCustomerOutputDTO.getMessage());
        return (JSONObject) JSON.toJSON(deleteCustomerOutputVO);
    }

    @RequestMapping("/customer/updateCustomer")
    public JSONObject updateCustomer (@RequestBody @Valid CustomerVO updateCustomerInputVO) {
        if(log.isInfoEnabled()){
            log.info("updateCustomer request: {}", updateCustomerInputVO);
        }
        //VO转DTO
        CustomerDTO updateCustomerInputDTO = new CustomerDTO();
        updateCustomerInputDTO.setCustomerId(updateCustomerInputVO.getCustomerId());
        updateCustomerInputDTO.setCustomerName(updateCustomerInputVO.getCustomerName());
        updateCustomerInputDTO.setCustomerGender(updateCustomerInputVO.getCustomerGender());
        updateCustomerInputDTO.setCustomerAge(updateCustomerInputVO.getCustomerAge());
        //DTO传入service层，返回DTO
        StatusMessageDTO updateCustomerOutputDTO = managerService.updateCustomer(updateCustomerInputDTO);
        //DTO转VO
        StatusMessageVO updateProductOutputVO = new StatusMessageVO();
        updateProductOutputVO.setStatus(updateCustomerOutputDTO.getStatus());
        updateProductOutputVO.setMessage(updateCustomerOutputDTO.getMessage());
        return (JSONObject) JSON.toJSON(updateProductOutputVO);
    }

}
