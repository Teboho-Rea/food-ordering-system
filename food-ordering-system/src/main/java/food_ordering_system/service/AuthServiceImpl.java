package food_ordering_system.service;

import food_ordering_system.dto.LoginRequest;
import food_ordering_system.dto.LoginResponse;
import food_ordering_system.dto.RegisterRequest;
import food_ordering_system.entity.Role;
import food_ordering_system.entity.User;
import food_ordering_system.exception.CategoryNotFoundException;
import food_ordering_system.repository.RoleRepository;
import food_ordering_system.repository.UserRepository;
import food_ordering_system.response.Response;
import food_ordering_system.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public Response<String> register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Email is already registered: " + request.getEmail());
        }

        // Every new user gets CUSTOMER role automatically
        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new CategoryNotFoundException(
                        "CUSTOMER role not found — check DataInitializer"));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .active(true)
                .roles(List.of(customerRole))
                .build();

        userRepository.save(user);

        return Response.success("Registration successful", null);
    }

    @Override
    public Response<LoginResponse> login(LoginRequest request) {
        // Use same generic message for wrong email and wrong password
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid credentials"));

        if (!user.isActive()) {
            throw new IllegalArgumentException(
                    "Account inactive. Please contact support.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .roles(roles)
                .build();

        return Response.success("Login successful", loginResponse);
    }
}