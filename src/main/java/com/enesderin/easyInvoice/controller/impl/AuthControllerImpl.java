package com.enesderin.easyInvoice.controller.impl;

import com.enesderin.easyInvoice.controller.AuthController;
import com.enesderin.easyInvoice.controller.RestBaseController;
import com.enesderin.easyInvoice.controller.RootEntity;
import com.enesderin.easyInvoice.dto.auth.*;
import com.enesderin.easyInvoice.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthControllerImpl extends RestBaseController implements AuthController {

    private AuthService authService;

    @PostMapping("/register")
    @Override
    public RootEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = this.authService.register(registerRequest);
        return ok(registerResponse);
    }

    @PostMapping("/login")
    @Override
    public RootEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ok(response);
    }

    @PostMapping("/refreshToken")
    @Override
    public RootEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshTokenResponse refreshTokenResponse = authService.refreshToken(refreshTokenRequest);
        return ok(refreshTokenResponse);
    }
}
