package com.enesderin.easyInvoice.controller;

import com.enesderin.easyInvoice.dto.request.CustomerRequest;
import com.enesderin.easyInvoice.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerController {
    RootEntity<List<CustomerResponse>> getCustomers(Long companyId);
    RootEntity<CustomerResponse> createCustomer(CustomerRequest customerRequest);
    RootEntity<CustomerResponse> getCustomer(Long id);
    RootEntity<CustomerResponse> updateCustomer(Long id, CustomerRequest customerRequest);
    RootEntity<String> deleteCustomer(Long id);
}
