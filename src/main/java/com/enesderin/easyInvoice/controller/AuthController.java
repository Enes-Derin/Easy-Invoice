package com.enesderin.easyInvoice.controller;

import com.enesderin.easyInvoice.dto.auth.*;

public interface AuthController {
    RootEntity<RegisterResponse> register(RegisterRequest registerRequest);
    RootEntity<LoginResponse> login(LoginRequest loginRequest);
    RootEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
