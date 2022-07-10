package com.example.cabmanagement.models.dao;

import com.example.cabmanagement.models.common.CabStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table
@Entity
@Data
@NoArgsConstructor
public class CabEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cityCode;
    @Enumerated(EnumType.STRING)
    private CabStatus cabStatus;
    private LocalDateTime cabOnboardingTime;
}
