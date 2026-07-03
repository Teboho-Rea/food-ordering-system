package food_ordering_system.service;

import food_ordering_system.dto.LoginRequest;
import food_ordering_system.dto.LoginResponse;
import food_ordering_system.dto.RegisterRequest;
import food_ordering_system.response.Response;

public interface AuthService {
    Response<String> register(RegisterRequest request);
    Response<LoginResponse> login(LoginRequest request);
}