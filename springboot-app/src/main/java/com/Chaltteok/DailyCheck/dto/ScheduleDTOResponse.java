package com.Chaltteok.DailyCheck.dto;

import com.Chaltteok.DailyCheck.entity.ScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleDTOResponse {
    private long userId;
    private long seniorId;
    private Date date;
    private LocalDateTime scheduleTime;

    public static ScheduleDTOResponse fromEntity(ScheduleEntity scheduleEntity) {
        return new ScheduleDTOResponse(
                scheduleEntity.getUser().getId(),
                scheduleEntity.getSenior().getId(),
                scheduleEntity.getDate(),
                scheduleEntity.getScheduleTime()
        );
    }
}
