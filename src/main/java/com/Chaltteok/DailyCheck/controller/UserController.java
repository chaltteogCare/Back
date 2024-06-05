package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.RegisterDTO;
import com.Chaltteok.DailyCheck.entity.UserEntity;
import com.Chaltteok.DailyCheck.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody RegisterDTO registerDTO) {
        UserEntity newUser = userService.registerUser(registerDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
