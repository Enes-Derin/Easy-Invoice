package com.enesderin.easyInvoice.dto.auth;

import com.enesderin.easyInvoice.model.RefreshToken;
import com.enesderin.easyInvoice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Role role;
    private Long companyId;
}
