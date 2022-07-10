package com.example.cabmanagement.services;

import com.example.cabmanagement.exceptions.CityNotFoundException;
import com.example.cabmanagement.models.dao.CityEntity;
import com.example.cabmanagement.models.service.City;
import com.example.cabmanagement.repositories.ICityRepository;
import com.example.cabmanagement.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private ICityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll()
                .stream()
                .map(cityEntity -> CopyUtil.deepCopy(cityEntity, City.class))
                .collect(Collectors.toList());
    }

    public List<City> onboardCities(List<City> citiesToOnboard) {
        List<City> citiesOnboarded = new LinkedList<>();
        for (City cityToOnboard : citiesToOnboard) {
            if (onboardCity(cityToOnboard)) {
                citiesOnboarded.add(cityToOnboard);
            }
        }
        return citiesOnboarded;
    }

    public boolean onboardCity(City city) {
        CityEntity cityEntity = CopyUtil.deepCopy(city, CityEntity.class);
        try {
            cityRepository.save(cityEntity);
        } catch (Exception e) {
            System.out.println("City already onboarded");
            return false;
        }
        return true;
    }

    public City getCityFromCityCode(String cityCode) throws CityNotFoundException {
        Optional<CityEntity> optionalCityEntity = cityRepository.findById(cityCode);
        if (optionalCityEntity.isPresent()) {
            return CopyUtil.deepCopy(optionalCityEntity.get(), City.class);
        }
        throw new CityNotFoundException();
    }

    public void validateCityOnBoarded(String cityCode) throws CityNotFoundException {
        getCityFromCityCode(cityCode);
    }
}
