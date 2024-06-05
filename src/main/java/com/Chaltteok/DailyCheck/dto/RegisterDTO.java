package com.Chaltteok.DailyCheck.dto;

import com.Chaltteok.DailyCheck.entity.UserEntity;
import lombok.*;
import org.apache.catalina.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String name;
    private String password;
    private String address;
    private String telephoneNumber;

    public UserEntity toUserEntity() {
        return UserEntity.builder()
                .name(this.name)
                .password(this.password)
                .address(this.address)
                .telephoneNumber(this.telephoneNumber).build();
    }
}
