package com.example.cabmanagement.controllers;

import com.example.cabmanagement.exceptions.CabBusyException;
import com.example.cabmanagement.exceptions.CabNotFoundException;
import com.example.cabmanagement.exceptions.CityNotFoundException;
import com.example.cabmanagement.models.dto.RestCab;
import com.example.cabmanagement.models.service.Cab;
import com.example.cabmanagement.services.CabService;
import com.example.cabmanagement.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CabController {

    @Autowired
    private CabService cabService;

    @GetMapping("/cabs")
    public List<RestCab> getAllCabs() {
        return cabService.getAllCabs().stream()
                .map(cab -> CopyUtil.deepCopy(cab, RestCab.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/cabs/register/bulk")
    public void bulkRegisterCabs(@RequestBody List<RestCab> cabsToRegister) throws CityNotFoundException {
        cabService.registerCabs(cabsToRegister.stream()
                .map(restCab -> CopyUtil.deepCopy(restCab, Cab.class))
                .collect(Collectors.toList()));
    }

    @PutMapping("/cab/updateCity")
    public void updateCabCity(@RequestBody RestCab cabToUpdateCity) throws CabNotFoundException, CabBusyException, CityNotFoundException {
        cabService.changeCityOfCab(CopyUtil.deepCopy(cabToUpdateCity, Cab.class));
    }
}
