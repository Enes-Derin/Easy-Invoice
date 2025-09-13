package com.enesderin.easyInvoice.service.impl;

import com.enesderin.easyInvoice.dto.request.CompanyRequest;
import com.enesderin.easyInvoice.dto.response.*;
import com.enesderin.easyInvoice.exception.BaseException;
import com.enesderin.easyInvoice.exception.ErrorMessage;
import com.enesderin.easyInvoice.exception.MessageType;
import com.enesderin.easyInvoice.model.*;
import com.enesderin.easyInvoice.repository.*;
import com.enesderin.easyInvoice.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private CustomerRepository customerRepository;
    private InvoiceRepository invoiceRepository;
    private UserRepository userRepository;
    private InvoiceItemRepository invoiceItemRepository;

    @Override
    public CompanyResponse createCompany(CompanyRequest companyRequestDto) {
        Company company = new Company();
        company.setName(companyRequestDto.getName());
        company.setAddress(companyRequestDto.getAddress());
        company.setPhone(companyRequestDto.getPhone());
        company.setEmail(companyRequestDto.getEmail());
        company.setTaxNumber(companyRequestDto.getTaxNumber());
        this.companyRepository.save(company);
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        companyResponse.setAddress(company.getAddress());
        companyResponse.setPhone(company.getPhone());
        companyResponse.setEmail(company.getEmail());
        companyResponse.setTaxNumber(company.getTaxNumber());
        return companyResponse;
    }

    @Override
    public CompanyResponse updateCompany(Long id, CompanyRequest companyRequestDto) {
        Optional<Company> optionalCompany = this.companyRepository.findById(id);
        if (optionalCompany.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, companyRequestDto.getName()));
        }
        Company company = optionalCompany.get();
        company.setName(companyRequestDto.getName());
        company.setAddress(companyRequestDto.getAddress());
        company.setPhone(companyRequestDto.getPhone());
        company.setEmail(companyRequestDto.getEmail());
        company.setTaxNumber(companyRequestDto.getTaxNumber());
        this.companyRepository.save(company);
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        companyResponse.setAddress(company.getAddress());
        companyResponse.setPhone(company.getPhone());
        companyResponse.setEmail(company.getEmail());
        companyResponse.setTaxNumber(company.getTaxNumber());
        return companyResponse;
    }

    @Override
    public CompanyResponse getCompany(Long id) {
        Optional<Company> optional = this.companyRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,optional.get().getName()));
        }

        // COMPANY İÇERİSİNDE BULUNAN CUSTOMER İÇİN
        List<Customer> customers = this.customerRepository.findCustomersByCompanyId(id);
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(customer.getId());
            customerResponse.setName(customer.getName());
            customerResponse.setAddress(customer.getAddress());
            customerResponse.setPhone(customer.getPhone());
            customerResponse.setEmail(customer.getEmail());
            customerResponse.setTaxNumber(customer.getTaxNumber());
            customerResponses.add(customerResponse);
        }
        // COMPANY İÇERİSİNDE BULUNAN INVOİCE VE INVOİCEITEM İÇİN
        List<Invoice> invoices = this.invoiceRepository.findAllByCompanyId(id);
        List<InvoiceItemResponse> invoiceItemResponses = new ArrayList<>();
        List<InvoiceResponse> invoiceResponses = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceResponse invoiceResponse = new InvoiceResponse();
            invoiceResponse.setId(invoice.getId());
            invoiceResponse.setIssueDate(invoice.getIssueDate());
            invoiceResponse.setDueDate(invoice.getDueDate());
            invoiceResponse.setTotalAmount(invoice.getTotalAmount());
            List<InvoiceItem> invoiceItems = this.invoiceItemRepository.findByInvoiceId(invoice.getId());
            for (InvoiceItem invoiceItem : invoiceItems) {
                InvoiceItemResponse invoiceItemResponse = new InvoiceItemResponse();
                invoiceItemResponse.setId(invoiceItem.getId());
                invoiceItemResponse.setQuantity(invoiceItem.getQuantity());
                invoiceItemResponse.setDescription(invoiceItem.getDescription());
                invoiceItemResponse.setTotalPrice(invoiceItem.getTotalPrice());
                invoiceItemResponse.setUnitPrice(invoiceItem.getUnitPrice());
                invoiceItemResponses.add(invoiceItemResponse);
            }
            invoiceResponse.setInvoiceItems(invoiceItemResponses);
            invoiceResponses.add(invoiceResponse);
        }
        // COMPANY İÇERİSİNDE BULUNAN USERS İÇİN
        List<User> users = this.userRepository.findAllByCompanyId(id);
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setPassword(user.getPassword());
            userResponse.setRole(user.getRole());
            userResponse.setEmail(user.getEmail());
            userResponses.add(userResponse);
        }

        Company company = optional.get();
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        companyResponse.setAddress(company.getAddress());
        companyResponse.setPhone(company.getPhone());
        companyResponse.setEmail(company.getEmail());
        companyResponse.setTaxNumber(company.getTaxNumber());
        companyResponse.setCustomers(customerResponses);
        companyResponse.setInvoices(invoiceResponses);
        companyResponse.setUsers(userResponses);
        return companyResponse;
    }

    @Override
    public List<CompanyResponse> getAllCompany() {
        List<Company> companyList = this.companyRepository.findAll();
        List<CompanyResponse> companyResponses = new ArrayList<>();
        for (Company company : companyList) {
            CompanyResponse companyResponse = new CompanyResponse();
            companyResponse.setId(company.getId());
            companyResponse.setName(company.getName());
            companyResponse.setAddress(company.getAddress());
            companyResponse.setPhone(company.getPhone());
            companyResponse.setEmail(company.getEmail());
            companyResponse.setTaxNumber(company.getTaxNumber());
            companyResponses.add(companyResponse);
        }
        return companyResponses;
    }

    @Override
    public String deleteCompany(Long id) {
        this.companyRepository.deleteById(id);
        return "Company deleted";
    }


}
