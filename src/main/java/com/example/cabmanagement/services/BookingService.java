package com.example.cabmanagement.services;

import com.example.cabmanagement.exceptions.AllCabsBusyException;
import com.example.cabmanagement.exceptions.CabNotFoundException;
import com.example.cabmanagement.exceptions.CityNotFoundException;
import com.example.cabmanagement.exceptions.TripNotFoundException;
import com.example.cabmanagement.models.common.CabStatus;
import com.example.cabmanagement.models.service.Cab;
import com.example.cabmanagement.models.service.City;
import com.example.cabmanagement.models.service.Trip;
import com.example.cabmanagement.services.cabfinder.ICabFinderStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private ICabFinderStrategy cabFinderStrategy;

    @Autowired
    private TripService tripService;

    @Autowired
    private CabService cabService;

    public Trip bookCabAndStartTrip(City city) throws AllCabsBusyException, CityNotFoundException, CabNotFoundException {
        Cab cab = cabFinderStrategy.findCab(city);
        Trip trip = tripService.createTrip(cab, city);
        cabService.updateCabStatus(cab, CabStatus.ON_TRIP);
        return trip;
    }

    public Trip endTrip(Long cabId) throws CabNotFoundException, TripNotFoundException {
        Cab cab = cabService.findCabById(cabId);
        Trip trip = tripService.endTrip(cab);
        cabService.updateCabStatus(cab, CabStatus.IDLE);
        return trip;
    }

    public List<Trip> getAllTripsForCab(Long cabId) throws CabNotFoundException {
        cabService.findCabById(cabId);
        return tripService.getAllTripsByCab(cabId);
    }

    public List<Trip> getAllTripsForCab() throws CabNotFoundException {
        return tripService.getAllTrips();
    }
}
