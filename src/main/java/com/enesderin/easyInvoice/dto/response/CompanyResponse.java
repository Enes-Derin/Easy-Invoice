package com.enesderin.easyInvoice.dto.response;

import lombok.Data;

import java.util.List;


@Data
public class CompanyResponse {
    private Long id;
    private String name;
    private String taxNumber;//vergi numarası
    private String address;
    private String phone;
    private String email;
    private List<UserResponse> users;
    private List<CustomerResponse> customers;
    private List<InvoiceResponse> invoices;

}
