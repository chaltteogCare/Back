package com.Chaltteok.DailyCheck.repository;

import com.Chaltteok.DailyCheck.entity.SeniorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.List;

public interface SeniorRepository extends JpaRepository<SeniorEntity, Long> {
    List<SeniorEntity> findByUserId(long userId);
=======
public interface SeniorRepository extends JpaRepository<SeniorEntity,Long> {
>>>>>>> origin/main
}
