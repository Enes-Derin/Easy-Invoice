package com.enesderin.easyInvoice.service;

import com.enesderin.easyInvoice.dto.auth.*;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
