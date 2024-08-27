package com.Chaltteok.DailyCheck.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String name;
    private String password;
    private String address;
    private String telephoneNumber;
}
