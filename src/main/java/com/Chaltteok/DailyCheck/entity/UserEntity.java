package com.Chaltteok.DailyCheck.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", unique = true, nullable = false)
    private int id;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 100, nullable = false)
    private String telephoneNumber;

//    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
//    private List<ScheduleEntity> schedules = new ArrayList<>();
}