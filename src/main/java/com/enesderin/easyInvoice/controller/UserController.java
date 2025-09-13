package com.enesderin.easyInvoice.controller;

import com.enesderin.easyInvoice.dto.request.UserRequest;
import com.enesderin.easyInvoice.dto.response.UserResponse;

import java.util.List;

public interface UserController {
    RootEntity<List<UserResponse>> getUsers(Long companyId);
    RootEntity<UserResponse> createUser(UserRequest userRequest);
    RootEntity<UserResponse> getUser(Long id);
    RootEntity<String> deleteUser(Long id);
}

