package com.example.purchase.service;

import com.example.purchase.dto.*;
import com.example.purchase.entity.Customer;
import com.example.purchase.entity.Product;
import com.example.purchase.sqlmap.ManagerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.example.purchase.constant.Message.*;
import static com.example.purchase.constant.Status.*;

/**
 * ManagerService class
 * description: 管理员系统业务处理
 *
 * @author huangyiran
 * @date 2022/9/12
 */

@Slf4j
@Service
public class ManagerService {

    @Autowired
    ManagerMapper managerMapper;

    public ArrayList<ProductDTO> listProducts () {
        //调用mapper层，获取DO
        ArrayList<Product> listProductsOutputDOs = managerMapper.listProducts();
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

    public StatusMessageDTO insertProduct (ProductDTO insertProductInputDTO) {
        Product insertProductDO = new Product();
        insertProductDO.setProductId(insertProductInputDTO.getProductId());
        insertProductDO.setProductName(insertProductInputDTO.getProductName());
        insertProductDO.setTotalAmount(insertProductInputDTO.getTotalAmount());
        insertProductDO.setAmountLowerLimit(insertProductInputDTO.getAmountLowerLimit());
        insertProductDO.setAmountUpperLimit(insertProductInputDTO.getAmountUpperLimit());
        insertProductDO.setStartSellDate(insertProductInputDTO.getStartSellDate());
        insertProductDO.setStopSellDate(insertProductInputDTO.getStopSellDate());
        insertProductDO.setYearInterestRate(insertProductInputDTO.getYearInterestRate());
        insertProductDO.setAmountLeft(insertProductInputDTO.getAmountLeft());

        StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
        //查询数据库中是否已经有此产品
        //有此产品则返回错误信息
        if (null != managerMapper.getProduct(insertProductDO)) {
            statusMessageDTO.setStatus(STATUS_FAILURE);
            statusMessageDTO.setMessage(INSERT_PRODUCT_FAILURE);
            if(log.isInfoEnabled()){
                log.info("insertProduct: failure");
            }
            return statusMessageDTO;
        }
        //无此产品则添加此产品
        try {
            managerMapper.insertProduct(insertProductDO);
        } catch (DuplicateKeyException e) {
            statusMessageDTO.setStatus(STATUS_FAILURE);
            statusMessageDTO.setMessage(INSERT_PRODUCT_FAILURE);
            if(log.isInfoEnabled()){
                log.info("insertProduct: failure");
            }
            return statusMessageDTO;
        }
        statusMessageDTO.setStatus(STATUS_SUCCESS);
        statusMessageDTO.setMessage(PRODUCT_SUCCESS);
        return statusMessageDTO;
    }

    public StatusMessageDTO deleteProduct (ProductIdDTO deleteProductInputDTO) {
        Product deleteProductDO = new Product();
        deleteProductDO.setProductId(deleteProductInputDTO.getProductId());
        StatusMessageDTO statusMessageDTO = new StatusMessageDTO();

        //查询数据库中是否有此产品
        //无此产品则报错
        if (null == managerMapper.getProduct(deleteProductDO)) {
            statusMessageDTO.setStatus(STATUS_FAILURE);
            statusMessageDTO.setMessage(DELETE_PRODUCT_FAILURE);
            if(log.isInfoEnabled()){
                log.info("deleteProduct: failure");
            }
            return statusMessageDTO;
        }
        //有此产品则删除
        managerMapper.deleteProduct(deleteProductDO);
        statusMessageDTO.setStatus(STATUS_SUCCESS);
        statusMessageDTO.setMessage(PRODUCT_SUCCESS);
        return statusMessageDTO;
    }

    public StatusMessageDTO updateProduct (ProductDTO updateProductInputDTO) {
        Product updateProductDO = new Product();
        updateProductDO.setProductId(updateProductInputDTO.getProductId());
        updateProductDO.setProductName(updateProductInputDTO.getProductName());
        updateProductDO.setTotalAmount(updateProductInputDTO.getTotalAmount());
        updateProductDO.setAmountLowerLimit(updateProductInputDTO.getAmountLowerLimit());
        updateProductDO.setAmountUpperLimit(updateProductInputDTO.getAmountUpperLimit());
        updateProductDO.setStartSellDate(updateProductInputDTO.getStartSellDate());
        updateProductDO.setStopSellDate(updateProductInputDTO.getStopSellDate());
        updateProductDO.setYearInterestRate(updateProductInputDTO.getYearInterestRate());
        updateProductDO.setAmountLeft(updateProductInputDTO.getAmountLeft());

        StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
        //查询数据库中是否有此产品
        //无此产品则报错
        if (null == managerMapper.getProduct(updateProductDO)) {
            statusMessageDTO.setStatus(STATUS_FAILURE);
            statusMessageDTO.setMessage(DELETE_PRODUCT_FAILURE);
            if(log.isInfoEnabled()){
                log.info("updateProduct: failure");
            }
            return statusMessageDTO;
        }
        //有此产品则更新
        managerMapper.updateProduct(updateProductDO);
        statusMessageDTO.setStatus(STATUS_SUCCESS);
        statusMessageDTO.setMessage(PRODUCT_SUCCESS);
        return statusMessageDTO;
    }

    public ArrayList<CustomerDTO> listCustomers () {
        //调用mapper层，获取DO
        ArrayList<Customer> listCustomersOutputDOs = managerMapper.listCustomers();
        //DO转DTO
        ArrayList<CustomerDTO> listCustomersOutputDTOs = new ArrayList<>();
        for (Customer listCustomersOutputDO : listCustomersOutputDOs) {
            CustomerDTO listCustomersDTO = new CustomerDTO();
            listCustomersDTO.setCustomerId(listCustomersOutputDO.getCustomerId());
            listCustomersDTO.setCustomerName(listCustomersOutputDO.getCustomerName());
            listCustomersDTO.setCustomerGender(listCustomersOutputDO.getCustomerGender());
            listCustomersDTO.setCustomerAge(listCustomersOutputDO.getCustomerAge());

            listCustomersOutputDTOs.add(listCustomersDTO);
        }
        return listCustomersOutputDTOs;
    }

    public StatusMessageDTO insertCustomer (CustomerDTO insertCustomerInputDTO) {
        Customer insertCustomerDO = new Customer();
        insertCustomerDO.setCustomerId(insertCustomerInputDTO.getCustomerId());
        insertCustomerDO.setCustomerName(insertCustomerInputDTO.getCustomerName());
        insertCustomerDO.setCustomerGender(insertCustomerInputDTO.getCustomerGender());
        insertCustomerDO.setCustomerAge(insertCustomerInputDTO.getCustomerAge());

        StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
        //查询数据库中是否已经有此用户
        //有此用户则返回错误信息
        if (null != managerMapper.getCustomer(insertCustomerDO)) {
            statusMessageDTO.setStatus(STATUS_FAILURE);
            statusMessageDTO.setMessage(INSERT_CUSTOMER_FAILURE);
            if(log.isInfoEnabled()){
                log.info("insertCustomer: failure");
            }
            return statusMessageDTO;
        }
        //无此用户则添加此用户
        try {
            managerMapper.insertCustomer(insertCustomerDO);
        } catch (DuplicateKeyException e) {
            statusMessageDTO.setStatus(STATUS_FAILURE);
            statusMessageDTO.setMessage(INSERT_CUSTOMER_FAILURE);
            if(log.isInfoEnabled()){
                log.info("insertCustomer: failure");
            }
            return statusMessageDTO;
        }
        statusMessageDTO.setStatus(STATUS_SUCCESS);
        statusMessageDTO.setMessage(CUSTOMER_SUCCESS);
        return statusMessageDTO;
    }

    public StatusMessageDTO deleteCustomer (CustomerIdDTO deleteCustomerInputDTO) {
        Customer deleteCustomerDO = new Customer();
        deleteCustomerDO.setCustomerId(deleteCustomerInputDTO.getCustomerId());
        StatusMessageDTO statusMessageDTO = new StatusMessageDTO();

        //查询数据库中是否有此永固
        //无此用户则报错
        if (null == managerMapper.getCustomer(deleteCustomerDO)) {
            statusMessageDTO.setStatus(STATUS_FAILURE);
            statusMessageDTO.setMessage(DELETE_CUSTOMER_FAILURE);
            if(log.isInfoEnabled()){
                log.info("deleteCustomer: failure");
            }
            return statusMessageDTO;
        }
        //有此用户则删除
        managerMapper.deleteCustomer(deleteCustomerDO);
        statusMessageDTO.setStatus(STATUS_SUCCESS);
        statusMessageDTO.setMessage(CUSTOMER_SUCCESS);
        return statusMessageDTO;
    }

    public StatusMessageDTO updateCustomer (CustomerDTO updateCustomerInputDTO) {
        Customer updateCustomerDO = new Customer();
        updateCustomerDO.setCustomerId(updateCustomerInputDTO.getCustomerId());
        updateCustomerDO.setCustomerName(updateCustomerInputDTO.getCustomerName());
        updateCustomerDO.setCustomerGender(updateCustomerInputDTO.getCustomerGender());
        updateCustomerDO.setCustomerAge(updateCustomerInputDTO.getCustomerAge());


        StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
        //查询数据库中是否有此用户
        //无此用户则报错
        if (null == managerMapper.getCustomer(updateCustomerDO)) {
            statusMessageDTO.setStatus(STATUS_FAILURE);
            statusMessageDTO.setMessage(DELETE_CUSTOMER_FAILURE);
            if(log.isInfoEnabled()){
                log.info("updateCustomer: failure");
            }
            return statusMessageDTO;
        }
        //有此用户则更新
        managerMapper.updateCustomer(updateCustomerDO);
        statusMessageDTO.setStatus(STATUS_SUCCESS);
        statusMessageDTO.setMessage(CUSTOMER_SUCCESS);
        return statusMessageDTO;
    }
}
