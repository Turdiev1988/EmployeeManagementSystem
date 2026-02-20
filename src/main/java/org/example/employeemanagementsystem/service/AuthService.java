package org.example.employeemanagementsystem.service;

import org.example.employeemanagementsystem.dto.JwtAuthResponse;
import org.example.employeemanagementsystem.dto.LoginDto;
import org.example.employeemanagementsystem.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);
}
