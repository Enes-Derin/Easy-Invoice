package com.enesderin.easyInvoice.service.impl;

import com.enesderin.easyInvoice.dto.request.CustomerRequest;
import com.enesderin.easyInvoice.dto.response.CustomerResponse;
import com.enesderin.easyInvoice.exception.BaseException;
import com.enesderin.easyInvoice.exception.ErrorMessage;
import com.enesderin.easyInvoice.exception.MessageType;
import com.enesderin.easyInvoice.model.Company;
import com.enesderin.easyInvoice.model.Customer;
import com.enesderin.easyInvoice.repository.CompanyRepository;
import com.enesderin.easyInvoice.repository.CustomerRepository;
import com.enesderin.easyInvoice.repository.InvoiceRepository;
import com.enesderin.easyInvoice.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private CompanyRepository companyRepository;
    private InvoiceRepository invoiceRepository;

    @Override
    public List<CustomerResponse> getCustomers(Long companyId) {
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        for (Customer customer : this.customerRepository.findCustomersByCompanyId(companyId)) {
            CustomerResponse customerResponse = new CustomerResponse();
            BeanUtils.copyProperties(customer, customerResponse);
            customerResponseList.add(customerResponse);
        }
        return customerResponseList;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRequest, customer);
        Optional<Company> company = companyRepository.findById(customerRequest.getCompanyId());
        if (company.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Company not found"));
        }
        customer.setCompany(company.get());
        this.customerRepository.save(customer);
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(customer, customerResponse);
        return customerResponse;
    }

    @Override
    public CustomerResponse getCustomer(Long id) {
        Optional<Customer> customer = this.customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Customer not found"));
        }
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(customer.get(), customerResponse);
        return customerResponse;
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Customer not found"));
        }
        Optional<Company> optionalCompany = this.companyRepository.findById(customerRequest.getCompanyId());
        BeanUtils.copyProperties(customerRequest, optionalCustomer.get());
        optionalCustomer.get().setCompany(optionalCompany.get());
        this.customerRepository.save(optionalCustomer.get());
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(optionalCustomer.get(), customerResponse);
        return customerResponse;
    }

    @Override
    public String deleteCustomer(Long id) {
        this.customerRepository.deleteById(id);
        this.invoiceRepository.deleteInvoiceByCustomerId(id);
        return "Customer deleted";
    }
}
