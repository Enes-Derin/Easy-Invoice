package com.enesderin.easyInvoice.dto.response;

import com.enesderin.easyInvoice.model.Company;
import lombok.Data;

@Data
public class CustomerResponse {

    private Long id;
    private String taxNumber; // vergi numarası
    private String name;
    private String address;
    private String phone;
    private String email;
    private Company company;
}
