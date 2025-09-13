package com.enesderin.easyInvoice.service;

import com.enesderin.easyInvoice.dto.request.UserRequest;
import com.enesderin.easyInvoice.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers(Long companyId);
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUser(Long id);
    String deleteUser(Long id);
}
