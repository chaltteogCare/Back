package com.Chaltteok.DailyCheck.service;

import com.Chaltteok.DailyCheck.Repository.UserRepository;
import com.Chaltteok.DailyCheck.dto.RegisterDTO;
import com.Chaltteok.DailyCheck.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity registerUser(RegisterDTO registerDTO) {
        UserEntity newUser = registerDTO.toUserEntity();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    
}
