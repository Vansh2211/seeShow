package com.seeshow.backend.service;

import com.seeshow.backend.dto.auth.AuthenticationResponse;
import com.seeshow.backend.dto.auth.LoginRequest;
import com.seeshow.backend.dto.auth.RegisterRequest;

public interface AuthService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(LoginRequest request);
}