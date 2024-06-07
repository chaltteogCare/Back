package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.RegisterDTO;
import com.Chaltteok.DailyCheck.entity.UserEntity;
import com.Chaltteok.DailyCheck.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody RegisterDTO registerDTO) {
        UserEntity newUser = userService.registerUser(registerDTO.toUserEntity());
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
