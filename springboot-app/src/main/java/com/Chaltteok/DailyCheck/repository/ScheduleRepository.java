package com.Chaltteok.DailyCheck.repository;

import com.Chaltteok.DailyCheck.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByUser_Id(long id);
}
