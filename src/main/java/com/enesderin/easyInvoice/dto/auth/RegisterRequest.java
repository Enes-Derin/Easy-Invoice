package com.enesderin.easyInvoice.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotNull
    private Long companyId;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String username;
}
