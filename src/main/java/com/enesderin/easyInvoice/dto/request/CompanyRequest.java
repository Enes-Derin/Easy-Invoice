package com.enesderin.easyInvoice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CompanyRequest {


    @NotNull
    private String name;
    @NotNull
    private String taxNumber;//vergi numarası
    private String address;
    private String phone;
    private String email;
}
