package com.enesderin.easyInvoice.dto.response;

import lombok.Data;


@Data
public class CompanyResponse {
    private Long id;
    private String name;
    private String taxNumber;//vergi numarası
    private String address;
    private String phone;
    private String email;
    private String logoUrl;
    private Long userId;
}
