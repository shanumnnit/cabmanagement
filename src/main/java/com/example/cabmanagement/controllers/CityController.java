package com.example.cabmanagement.controllers;

import com.example.cabmanagement.exceptions.CityNotFoundException;
import com.example.cabmanagement.models.dto.RestCity;
import com.example.cabmanagement.models.service.City;
import com.example.cabmanagement.services.CityService;
import com.example.cabmanagement.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/cities")
    public List<RestCity> getAllCities() {
        return cityService.getAllCities().stream()
                .map(city -> CopyUtil.deepCopy(city, RestCity.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/cities/onboard/bulk")
    public List<RestCity> bulkRegisterCabs(@RequestBody List<RestCity> cityList) throws CityNotFoundException {
        List<City> onboardedCities = cityService.onboardCities(cityList.stream()
                .map(RestCity -> CopyUtil.deepCopy(RestCity, City.class))
                .collect(Collectors.toList()));
        return onboardedCities.stream()
                .map(city -> CopyUtil.deepCopy(city, RestCity.class))
                .collect(Collectors.toList());
    }
}
