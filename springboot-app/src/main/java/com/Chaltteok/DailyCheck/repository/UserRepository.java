package com.Chaltteok.DailyCheck.repository;

import com.Chaltteok.DailyCheck.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //Optional<UserEntity> findByname(String name);
    UserEntity findByName(String name);
}
