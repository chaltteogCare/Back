package com.Chaltteok.DailyCheck.controller;

import com.Chaltteok.DailyCheck.dto.RegisterDTO;
import com.Chaltteok.DailyCheck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class RegisterController {
    private final UserService userService;

//    @PostMapping("/user")
//    public String signup(RegisterDTO request){
//        userService.save(request);
//        return "redirect:/login";
//    }
}
