package com.enesderin.easyInvoice.dto.request;

import com.enesderin.easyInvoice.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    @Email
    private String email;
    @NotNull
    private Role role;
    @NotNull
    private Long companyId;
}
