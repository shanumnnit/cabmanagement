package com.example.cabmanagement.services.cabfinder;

import com.example.cabmanagement.exceptions.AllCabsBusyException;
import com.example.cabmanagement.exceptions.CabNotFoundException;
import com.example.cabmanagement.exceptions.CityNotFoundException;
import com.example.cabmanagement.models.common.CabStatus;
import com.example.cabmanagement.models.service.Cab;
import com.example.cabmanagement.models.service.City;
import com.example.cabmanagement.services.CabService;
import com.example.cabmanagement.services.CityService;
import com.example.cabmanagement.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MinimizeIdleTimeCabFinderStrategy implements ICabFinderStrategy {

    @Autowired
    private CabService cabService;

    @Autowired
    private CityService cityService;

    @Autowired
    private TripService tripService;

    @Override
    public Cab findCab(City city) throws AllCabsBusyException, CityNotFoundException, CabNotFoundException {
        cityService.validateCityOnBoarded(city.getCityCode());
        List<Cab> availableCabsByCityCode = cabService.findCabsByCityId(city.getCityCode())
                .stream()
                .filter(cab -> !CabStatus.ON_TRIP.equals(cab.getCabStatus()))
                .collect(Collectors.toList());
        Cab maximumWaitingCab = null;
        Long waitingSince = null;
        if (!CollectionUtils.isEmpty(availableCabsByCityCode)) {
            for (Cab currentCab : availableCabsByCityCode) {

                //fetch the cabs last trip end time
                LocalDateTime currentCabWaitingSince = tripService.getCabLastTripEndTime(currentCab);

                //cab didn't have any trips yet, assume onboarding time
                if (null == currentCabWaitingSince) {
                    currentCabWaitingSince = cabService.findCabById(currentCab.getId()).getCabOnboardingTime();
                }

                Long currMillis = currentCabWaitingSince.toEpochSecond(ZoneOffset.UTC);
                System.out.println("Cab id " + currentCab.getId() + " waiting since " + currentCabWaitingSince);

                if (waitingSince == null || waitingSince > currMillis) {
                    waitingSince = currMillis;
                    maximumWaitingCab = currentCab;
                }
            }
        }
        if (null != maximumWaitingCab)
            return maximumWaitingCab;
        throw new AllCabsBusyException();
    }
}
