package com.enesderin.easyInvoice.controller.impl;

import com.enesderin.easyInvoice.controller.RootEntity;
import com.enesderin.easyInvoice.controller.UserController;
import com.enesderin.easyInvoice.dto.request.UserRequest;
import com.enesderin.easyInvoice.dto.response.UserResponse;
import com.enesderin.easyInvoice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserControllerImpl extends RootEntity implements UserController {

    private UserService userService;

    @GetMapping("/all/{companyId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @Override
    public RootEntity<List<UserResponse>> getUsers(@PathVariable Long companyId) {
        return ok(userService.getUsers(companyId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Override
    public RootEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ok(userService.createUser(userRequest));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<UserResponse> getUser(@PathVariable Long id) {
        return ok(userService.getUser(id));
    }

    @DeleteMapping("/{id}")
    @Override
    public RootEntity<String> deleteUser(@PathVariable Long id) {
        return ok(userService.deleteUser(id));
    }
}
