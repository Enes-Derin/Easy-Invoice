package com.enesderin.easyInvoice.dto.request;

import lombok.Data;


@Data
public class CompanyRequest {


    private String name;
    private String taxNumber;//vergi numarası
    private String address;
    private String phone;
    private String email;
    private String logoUrl;
}
