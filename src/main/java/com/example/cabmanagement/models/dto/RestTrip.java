package com.example.cabmanagement.models.dto;

import com.example.cabmanagement.models.common.TripStatus;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RestTrip {
    private Long tripId;
    @NotNull
    private Long cabId;
    @NotNull
    private String cityCode;
    private BigDecimal price;
    private TripStatus tripStatus;
    private LocalDateTime tripStartTime;
    private LocalDateTime tripEndTime;
}
