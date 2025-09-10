package com.enesderin.easyInvoice.dto.auth;

import lombok.Data;

@Data
public class RegisterResponse {
    private Long id;
    private String email;
    private String username;
    private String role;
    private Long companyId;
}
