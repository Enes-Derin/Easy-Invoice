package com.enesderin.easyInvoice.dto.response;

import com.enesderin.easyInvoice.model.Role;
import lombok.Data;

@Data
public class UserResponse {
    private long id;
    private String username;
    private String password;
    private String email;
    private Role role;
}
