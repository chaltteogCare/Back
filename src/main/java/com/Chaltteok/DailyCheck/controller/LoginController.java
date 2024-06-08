package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.LoginDTO;
import com.Chaltteok.DailyCheck.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class LoginController {
    private final UserService userService;

//    @PostMapping("/login")
//    public ResponseEntity<String> getUserProfile(
//            @Valid @RequestBody LoginDTO request
//    ){
//        String token = this.userService.login(request);
//        return ResponseEntity.status(HttpStatus.OK).body(token);
//    }
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
//        String token = userService.login(loginDTO);
//        return ResponseEntity.ok("Bearer " + token);
//    }
}
