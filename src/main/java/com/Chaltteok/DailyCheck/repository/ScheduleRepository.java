package com.Chaltteok.DailyCheck.repository;

import com.Chaltteok.DailyCheck.entity.ScheduleEntity;

import java.util.List;

public interface ScheduleRepository {
    List<ScheduleEntity> findByUser_Id(int id);
}
