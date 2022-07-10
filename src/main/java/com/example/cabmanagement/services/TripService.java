package com.example.cabmanagement.services;

import com.example.cabmanagement.exceptions.TripNotFoundException;
import com.example.cabmanagement.models.common.TripStatus;
import com.example.cabmanagement.models.dao.TripEntity;
import com.example.cabmanagement.models.service.Cab;
import com.example.cabmanagement.models.service.City;
import com.example.cabmanagement.models.service.Trip;
import com.example.cabmanagement.repositories.ITripRepository;
import com.example.cabmanagement.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {

    @Autowired
    private ITripRepository tripRepository;

    public Trip createTrip(Cab cab, City city) {
        TripEntity toSaveTripEnity = TripEntity.TripEntityBuilder.aTripEntity()
                .withCabId(cab.getId())
                .withCityCode(city.getCityCode())
                .withTripStartTime(LocalDateTime.now())
                .withTripStatus(TripStatus.INPROGRESS)
                .build();
        return CopyUtil.deepCopy(tripRepository.save(toSaveTripEnity), Trip.class);
    }

    public Trip endTrip(Cab cab) throws TripNotFoundException {
        TripEntity toEndTripEnity = tripRepository.findTop1ByCabIdAndTripEndTimeIsNullOrderByTripEndTimeDesc(cab.getId());
        if (toEndTripEnity == null)
            throw new TripNotFoundException();
        toEndTripEnity.setTripStatus(TripStatus.COMPLETED);
        toEndTripEnity.setTripEndTime(LocalDateTime.now());

        System.out.println(toEndTripEnity + " ended in " + ChronoUnit.SECONDS.between(toEndTripEnity.getTripStartTime(), toEndTripEnity.getTripEndTime()) + " seconds");

        return CopyUtil.deepCopy(tripRepository.save(toEndTripEnity), Trip.class);
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(tripEntity -> CopyUtil.deepCopy(tripEntity, Trip.class))
                .collect(Collectors.toList());
    }

    public List<Trip> getAllTripsByCab(Long cabId) {
        return tripRepository.findAllByCabIdOrderByTripStartTimeAsc(cabId).stream()
                .map(tripEntity -> CopyUtil.deepCopy(tripEntity, Trip.class))
                .collect(Collectors.toList());
    }

    public LocalDateTime getCabLastTripEndTime(Cab cab) {
        TripEntity tripEntity = tripRepository.findTop1ByCabIdAndTripStatusEqualsOrderByTripEndTimeDesc(cab.getId(), TripStatus.COMPLETED);
        if (null != tripEntity) {
            return tripEntity.getTripEndTime();
        }
        return null;
    }
}
