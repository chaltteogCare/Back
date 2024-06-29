package com.Chaltteok.DailyCheck.service;

import com.Chaltteok.DailyCheck.auth.JwtTokenProvider;
import com.Chaltteok.DailyCheck.dto.LoginDTO;
import com.Chaltteok.DailyCheck.dto.RegisterDTO;
import com.Chaltteok.DailyCheck.dto.UserDTO;
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

<<<<<<< HEAD
    public long save(RegisterDTO dto){
        return userRepository.save(UserEntity.builder()
=======
    public int save(RegisterDTO dto){
        return (int) userRepository.save(UserEntity.builder()
>>>>>>> origin/main
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
            throw new UsernameNotFoundException("No User found with name: " + name);
        }
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        String accessToken = jwtTokenProvider.createAccessToken(dto);
        return accessToken;
    }

    public void update(Long id, UserDTO dto){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("No User found with id: " + id));
        if(dto.getName() != null){
            user.setName(dto.getName());
        }
        if(dto.getTelephoneNumber() != null){
            user.setTelephoneNumber(dto.getTelephoneNumber());
        }
        if(dto.getAddress() != null){
            user.setAddress(dto.getAddress());
        }
        userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
