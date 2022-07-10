package com.example.cabmanagement.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
public class City {
    @NonNull
    private String cityCode;
}
