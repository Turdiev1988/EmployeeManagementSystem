package org.example.employeemanagementsystem.service.impl;

import lombok.AllArgsConstructor;
import org.example.employeemanagementsystem.dto.JwtAuthResponse;
import org.example.employeemanagementsystem.dto.LoginDto;
import org.example.employeemanagementsystem.dto.RegisterDto;
import org.example.employeemanagementsystem.entity.Role;
import org.example.employeemanagementsystem.entity.User;
import org.example.employeemanagementsystem.exception.EmployeeAPIException;
import org.example.employeemanagementsystem.repository.RoleRepository;
import org.example.employeemanagementsystem.repository.UserRepository;
import org.example.employeemanagementsystem.security.JwtTokenProvider;
import org.example.employeemanagementsystem.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {

        //check username is already exists in database
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new EmployeeAPIException(HttpStatus.BAD_REQUEST,"Username already exists!");
        }
        //check email is already exists in database
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new EmployeeAPIException(HttpStatus.BAD_REQUEST,"Email already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole != null) {
            roles.add(adminRole);
        }

        user.setRoles(roles);

        user.setRoles(roles);
        userRepository.save(user);
        return "User Registered Successfully";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginDto.getUsernameOrEmail(),
            loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
        String role = null;
        if (userOptional.isPresent()){
            User loggedInUser = userOptional.get();
            Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();
            if (optionalRole.isPresent()){
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }
}
