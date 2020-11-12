package com.inft.awm.service;


import com.inft.awm.domain.Customer;
import com.inft.awm.domain.Receipt;
import com.inft.awm.repository.CustomerRepository;
import com.inft.awm.repository.ReceiptRepository;
import com.inft.awm.response.ResponseCustomerLogin;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.token.TokenUtils;
import com.inft.awm.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Handle business logic
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ReceiptRepository receiptRepository;

    /**Register a new customer
     * @param customer
     */
    public void register(Customer customer) {
        Customer existCustomer = customerRepository.findByUserName(customer.getAccount_name());

        if (existCustomer != null) {
            throw new RuntimeException("The customer name has already been exist");
        }

        if (StringUtils.isEmpty(customer.getPortrait_url())) {
            customer.setPortrait_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602311307224&di=1ac5d67a99034e9078781ba583bd4de7&imgtype=0&src=http%3A%2F%2Fwww.51yuansu.com%2Fpic2%2Fcover%2F00%2F39%2F51%2F5812ec5184228_610.jpg");
        }

        customerRepository.save(customer);
    }

    /**Customer Login
     * @param account_name
     * @param password
     * @return
     */
    public ResponseCustomerLogin login(String account_name,String password) {

        Customer existCustomer = customerRepository.findByUserName(account_name);

        if (existCustomer == null) {
            throw new RuntimeException("The customer dos not exist");
        } else {
            if (!password.equals(existCustomer.getPassword())) {
                throw new RuntimeException("The password is wrong");
            } else {
                String token = TokenUtils.createCustomerToken(existCustomer);
                return new ResponseCustomerLogin(token,existCustomer);
            }
        }
    }

    /**Get all Customer
     * @return
     */
    public Iterable<Customer> getAllCustomers() {
        Iterable<Customer> allCustomers = customerRepository.findAll();
        return allCustomers;
    }

    /**Find a customer by id
     * @param customerId
     * @return
     */
    public Customer findCustomerById(Integer customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    /**get all receipts for a customer
     * @param customerId
     * @return receipt list
     */
    public List<Receipt> getReceipts(Integer customerId) {
        if (customerId == null) {
            throw new RuntimeException("The customer id is null");
        }
        List<Receipt> result = new ArrayList<>();
        Iterable<Receipt> customerReceipts = receiptRepository.findCustomerReceipts(customerId);
        Iterator<Receipt> iterator = customerReceipts.iterator();
        while (iterator.hasNext()) {
            Receipt receipt = iterator.next();
            result.add(receipt);
        }
        return result;
    }
}
