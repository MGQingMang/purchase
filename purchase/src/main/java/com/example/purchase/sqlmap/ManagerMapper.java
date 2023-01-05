package com.example.purchase.sqlmap;

import com.example.purchase.entity.Customer;
import com.example.purchase.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * ManagerMapper class
 * description: 管理员系统mapper
 *
 * @author huangyiran
 * @date 2022/9/6
 */
@Mapper
public interface ManagerMapper {
    public ArrayList<Product> listProducts();

    public void insertProduct (Product insertProductDO);

    public void deleteProduct (Product deleteProductDTO);

    public void updateProduct (Product updateProductDTO);

    public ArrayList<Customer> listCustomers();

    public void insertCustomer(Customer insertCustomerDTO);

    public void deleteCustomer(Customer deleteCustomerDTO);

    public void updateCustomer(Customer updateCustomerDTO);

    Product getProduct (Product productDO);

    Object getCustomer (Customer insertCustomerDO);
}
