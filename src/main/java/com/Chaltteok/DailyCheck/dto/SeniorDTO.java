package com.Chaltteok.DailyCheck.dto;

import com.Chaltteok.DailyCheck.entity.SeniorEntity;
import com.Chaltteok.DailyCheck.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeniorDTO {
    private long id;
    private long userId;
    private String name;
    private int age;
    private String address;
    private String telephoneNumber;
    private String notes;

    public SeniorEntity toEntity(UserEntity user){
        return SeniorEntity.builder()
                .id(this.id)
                .user(user)
                .name(this.name)
                .age(this.age)
                .address(this.address)
                .telephoneNumber(this.telephoneNumber)
                .notes(this.notes).build();
    }
}
