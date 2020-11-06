package com.inft.awm.service;


import com.inft.awm.domain.Customer;
import com.inft.awm.domain.Receipt;
import com.inft.awm.repository.CustomerRepository;
import com.inft.awm.repository.ReceiptRepository;
import com.inft.awm.response.ResponseCustomerLogin;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Handle customer business logic
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
