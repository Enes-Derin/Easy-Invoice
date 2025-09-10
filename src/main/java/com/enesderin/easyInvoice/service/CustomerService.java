package com.enesderin.easyInvoice.service;

import com.enesderin.easyInvoice.dto.request.CustomerRequest;
import com.enesderin.easyInvoice.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getCustomers(Long companyId);
    CustomerResponse createCustomer(CustomerRequest customerRequest);
    CustomerResponse getCustomer(Long id);
    CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest);
    String deleteCustomer(Long id);
}
