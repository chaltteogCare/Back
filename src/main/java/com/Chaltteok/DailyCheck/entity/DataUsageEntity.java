package com.Chaltteok.DailyCheck.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DataUsageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="datausage_id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="senior_id")
    private SeniorEntity senior;

    @Column(length = 15, nullable = false)
    private Date date;

    @Column(length = 15, nullable = false)
    private int phoneUsage;

    @Column(length = 15, nullable = false)
    private int waterUsage;

    @Column(length = 15, nullable = false)
    private int elecUsage;

    @Column(length = 15, nullable = false)
    private String status;
// 노인 id, 날짜, 폰, 수도, 전기 사용량, 오늘상태
}
