package com.example.cabmanagement.services;

import com.example.cabmanagement.exceptions.CabBusyException;
import com.example.cabmanagement.exceptions.CabNotFoundException;
import com.example.cabmanagement.exceptions.CityNotFoundException;
import com.example.cabmanagement.models.common.CabStatus;
import com.example.cabmanagement.models.dao.CabEntity;
import com.example.cabmanagement.models.service.Cab;
import com.example.cabmanagement.repositories.ICabRepository;
import com.example.cabmanagement.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CabService {

    @Autowired
    private ICabRepository cabRepository;

    @Autowired
    private CityService cityService;

    public List<Cab> getAllCabs() {
        return cabRepository.findAll()
                .stream()
                .map(cabEntity -> CopyUtil.deepCopy(cabEntity, Cab.class))
                .collect(Collectors.toList());
    }

    //bulk register cabs
    public List<Cab> registerCabs(List<Cab> cabToRegister) throws CityNotFoundException {
        List<Cab> savedCabs = new LinkedList<>();
        for (Cab cab : cabToRegister) {
            cityService.validateCityOnBoarded(cab.getCityCode());
        }
        for (Cab cab : cabToRegister) {
            CabEntity entity = CopyUtil.deepCopy(cab, CabEntity.class);
            entity.setCabStatus(CabStatus.IDLE);
            entity.setCabOnboardingTime(LocalDateTime.now());
            savedCabs.add(CopyUtil.deepCopy(cabRepository.save(entity), Cab.class));
        }
        return savedCabs;
    }

    //change city of cab
    public void changeCityOfCab(Cab cabToChangeCity) throws CabNotFoundException, CabBusyException, CityNotFoundException {
        Cab cab = findCabById(cabToChangeCity.getId());
        if (cab.getCabStatus().equals(CabStatus.ON_TRIP)) {
            throw new CabBusyException();
        }
        cityService.validateCityOnBoarded(cabToChangeCity.getCityCode());
        cab.setCityCode(cabToChangeCity.getCityCode());
        cabRepository.save(CopyUtil.deepCopy(cab, CabEntity.class));
    }

    //find cab from cab ID
    public Cab findCabById(Long cabId) throws CabNotFoundException {
        Optional<CabEntity> optionalCabEntity = cabRepository.findById(cabId);
        if (optionalCabEntity.isPresent()) {
            return CopyUtil.deepCopy(optionalCabEntity.get(), Cab.class);
        }
        throw new CabNotFoundException();
    }

    //find cabs from cityCode
    public List<Cab> findCabsByCityId(String cityCode) {
        return cabRepository.findCabEntitiesByCityCode(cityCode)
                .stream()
                .map(cabEntity -> CopyUtil.deepCopy(cabEntity, Cab.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    public void updateCabStatus(Cab cab, CabStatus cabStatus) {
        Optional<CabEntity> optionalCabEntity = cabRepository.findById(cab.getId());
        if (optionalCabEntity.isPresent()) {
            CabEntity cabEntity = optionalCabEntity.get();
            cabEntity.setCabStatus(cabStatus);
            cabRepository.save(cabEntity);
        }
    }
}
