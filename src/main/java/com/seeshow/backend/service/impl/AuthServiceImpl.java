package com.seeshow.backend.service.impl;

    import com.seeshow.backend.dto.auth.AuthenticationResponse;
import com.seeshow.backend.dto.auth.LoginRequest;
import com.seeshow.backend.dto.auth.RegisterRequest;
import com.seeshow.backend.entity.Role;
import com.seeshow.backend.entity.User;
import com.seeshow.backend.exception.EmailAlreadyExistsException;
import com.seeshow.backend.exception.ResourceNotFoundException;
import com.seeshow.backend.repository.RoleRepository;
import com.seeshow.backend.repository.UserRepository;
import com.seeshow.backend.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        // 1. Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // 2. Find default USER role
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() ->
                        new ResourceNotFoundException("USER role not found"));

        // 3. Create User entity
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        // 4. NEVER store plain-text password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // 5. Enable the account
        user.setEnabled(true);

        // 6. Assign USER role
        user.setRoles(Set.of(userRole));

        // 7. Save user
        User savedUser = userRepository.save(user);

        // JWT will be added later
        return AuthenticationResponse.builder()
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        return null;
    }
}