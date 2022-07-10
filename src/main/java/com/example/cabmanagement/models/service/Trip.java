package com.example.cabmanagement.models.service;

import com.example.cabmanagement.models.common.TripStatus;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Trip {
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
