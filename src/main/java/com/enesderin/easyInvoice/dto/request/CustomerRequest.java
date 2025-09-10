package com.enesderin.easyInvoice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CustomerRequest {

    @NotNull
    private String taxNumber; // vergi numarası
    @NotNull
    private String name;

    private String address;

    private String phone;

    private String email;
    @NotNull
    private Long companyId;
}
