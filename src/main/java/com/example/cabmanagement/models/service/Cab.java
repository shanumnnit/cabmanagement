package com.example.cabmanagement.models.service;

import com.example.cabmanagement.models.common.CabStatus;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Cab {
    private Long id;
    @NotNull
    private String cityCode;
    private CabStatus cabStatus;
    private LocalDateTime cabOnboardingTime;
}
