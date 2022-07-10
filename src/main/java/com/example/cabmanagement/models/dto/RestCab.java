package com.example.cabmanagement.models.dto;

import com.example.cabmanagement.models.common.CabStatus;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RestCab {
    private Long id;
    @NotNull
    private String cityCode;
    private CabStatus cabStatus;
    private LocalDateTime cabOnboardingTime;
}
