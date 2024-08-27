package com.Chaltteok.DailyCheck.dto;

import com.Chaltteok.DailyCheck.entity.SeniorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeniorDTOResponse {
    private long id;
    private long userId;
    private String name;
    private int age;
    private String address;
    private String telephoneNumber;
    private String notes;

    public static SeniorDTOResponse fromEntity(SeniorEntity seniorEntity) {
        return new SeniorDTOResponse(
                seniorEntity.getId(),
                seniorEntity.getUser().getId(),
                seniorEntity.getName(),
                seniorEntity.getAge(),
                seniorEntity.getAddress(),
                seniorEntity.getTelephoneNumber(),
                seniorEntity.getNotes()
        );
    }
}
