package com.inft.awm.service;


import com.inft.awm.domain.Customer;
import com.inft.awm.repository.CustomerRepository;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public void register(Customer customer) {
        Customer existCustomer = customerRepository.findByUserName(customer.getUser_name());

        if (existCustomer != null) {
            throw new RuntimeException("The customer name has already been exist");
        }

        customerRepository.save(customer);
    }

    public ResponseLogin login(Customer loginCustomer) {

        Customer existCustomer = customerRepository.findByUserName(loginCustomer.getUser_name());

        if (existCustomer == null) {
            throw new RuntimeException("The customer dos not exist");
        } else {
            if (!loginCustomer.getPassword().equals(existCustomer.getPassword())) {
                throw new RuntimeException("The password is wrong");
            } else {
                String token = TokenUtils.createCustomerToken(existCustomer);
                return new ResponseLogin(token);
            }
        }
    }

    public Iterable<Customer> getAllCustomers() {
        Iterable<Customer> allCustomers = customerRepository.findAll();
        return allCustomers;
    }

    public Customer findCustomerById(Integer customerId) {
        return customerRepository.findByCustomerId(customerId);
    }
}
