package com.example.cabmanagement.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RestCity {
    @NonNull
    private String cityCode;
}
