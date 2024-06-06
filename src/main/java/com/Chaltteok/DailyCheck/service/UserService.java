package com.Chaltteok.DailyCheck.service;

import com.Chaltteok.DailyCheck.dto.RegisterDTO;
import com.Chaltteok.DailyCheck.entity.UserEntity;
import com.Chaltteok.DailyCheck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public int save(RegisterDTO dto){
        return userRepository.save(UserEntity.builder()
                .name(dto.getName())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .address(dto.getAddress())
                .telephoneNumber(dto.getTelephoneNumber())
                .build()).getId();
    }
}
