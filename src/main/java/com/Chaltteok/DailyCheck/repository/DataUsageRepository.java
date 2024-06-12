package com.Chaltteok.DailyCheck.repository;

import com.Chaltteok.DailyCheck.entity.DataUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataUsageRepository extends JpaRepository<DataUsageEntity, Long> {
    //DataUsageEntity findById(long id);
    Optional<DataUsageEntity> findById(long id);
}
