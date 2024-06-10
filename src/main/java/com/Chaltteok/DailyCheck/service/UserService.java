package com.Chaltteok.DailyCheck.service;

import com.Chaltteok.DailyCheck.auth.JwtTokenProvider;
import com.Chaltteok.DailyCheck.dto.LoginDTO;
import com.Chaltteok.DailyCheck.dto.RegisterDTO;
import com.Chaltteok.DailyCheck.entity.UserEntity;
import com.Chaltteok.DailyCheck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public int save(RegisterDTO dto){
        return (int) userRepository.save(UserEntity.builder()
                .name(dto.getName())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .address(dto.getAddress())
                .telephoneNumber(dto.getTelephoneNumber())
                .build()).getId();
    }

    public String login(LoginDTO dto){
        String name = dto.getName();
        String password = dto.getPassword();
        UserEntity user = userRepository.findByName(name);
        if(user == null){
            throw new UsernameNotFoundException("사람 없음~");
        }
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("비밀번호 틀림~");
        }
        String accessToken = jwtTokenProvider.createAccessToken(dto);
        return accessToken;
    }
}
