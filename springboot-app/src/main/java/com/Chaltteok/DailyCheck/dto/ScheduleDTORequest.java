package com.Chaltteok.DailyCheck.dto;

import com.Chaltteok.DailyCheck.entity.ScheduleEntity;
import com.Chaltteok.DailyCheck.entity.SeniorEntity;
import com.Chaltteok.DailyCheck.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTORequest {
    private long userId;
    private long seniorId;
    private Date date;
    private LocalDateTime scheduleTime;

    public ScheduleEntity toEntity(UserEntity user, SeniorEntity senior){
        return ScheduleEntity.builder()
                .user(user)
                .senior(senior)
                .date(this.date)
                .scheduleTime(this.scheduleTime).build();
    }
}
