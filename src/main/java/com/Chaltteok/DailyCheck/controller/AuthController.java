package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.JwtTokenDto;
import com.Chaltteok.DailyCheck.entity.UserEntity;
import com.Chaltteok.DailyCheck.security.JwtProvider;
import com.Chaltteok.DailyCheck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JwtTokenDto login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = jwtProvider.createAccessToken(authentication.getName());
        return new JwtTokenDto(token, ""); // TODO: 리프레시토큰
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        UserEntity newUser = UserEntity.builder()
                .name(username)
                .password(password)
                .build();
        userService.registerUser(newUser);
        return "User registered successfully";
    }

}
