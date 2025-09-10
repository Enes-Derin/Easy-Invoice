package com.enesderin.easyInvoice.service.impl;

import com.enesderin.easyInvoice.dto.auth.*;
import com.enesderin.easyInvoice.exception.BaseException;
import com.enesderin.easyInvoice.exception.ErrorMessage;
import com.enesderin.easyInvoice.exception.MessageType;
import com.enesderin.easyInvoice.model.RefreshToken;
import com.enesderin.easyInvoice.model.Role;
import com.enesderin.easyInvoice.model.User;
import com.enesderin.easyInvoice.repository.RefreshTokenRepository;
import com.enesderin.easyInvoice.repository.UserRepository;
import com.enesderin.easyInvoice.security.JwtService;
import com.enesderin.easyInvoice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtService jwtService;

    private User createUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setRole(Role.MANAGER);
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        return user;
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis()+1000*60*60*4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        return refreshToken;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        User savedUser = this.userRepository.save(createUser(registerRequest));
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUsername(savedUser.getUsername());
        registerResponse.setEmail(savedUser.getEmail());
        registerResponse.setRole(Role.MANAGER.name());
        return registerResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            authenticationProvider.authenticate(authenticationToken);

            Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

            String accessToken = jwtService.createToken(user.get());

            RefreshToken refreshToken = refreshTokenRepository.save(createRefreshToken(user.get()));

            return new LoginResponse(accessToken,refreshToken.getRefreshToken(), user.get().getRole(),null);

        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
        }

    }

    public boolean isValidRefreshToken(Date expiryDate) {
        return new Date().before(expiryDate);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken());
        if (optRefreshToken.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND, refreshTokenRequest.getRefreshToken()));
        }
        if (!isValidRefreshToken(optRefreshToken.get().getExpiryDate())){
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPIRED, refreshTokenRequest.getRefreshToken()));
        }
        User user = optRefreshToken.get().getUser();
        String accessToken = jwtService.createToken(user);
        RefreshToken refreshToken = refreshTokenRepository.save(createRefreshToken(user));
        return new RefreshTokenResponse(accessToken,refreshToken.getRefreshToken());
    }
}
