package com.Chaltteok.DailyCheck.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_id", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(length = 15, nullable = false)
    private Date date;

    @Column(length = 15, nullable = false)
    private int orderNum;

    @Column(length = 20, nullable = false)
    private LocalDateTime scheduleTime;
}
