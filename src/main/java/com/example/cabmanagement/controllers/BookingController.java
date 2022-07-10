package com.example.cabmanagement.controllers;

import com.example.cabmanagement.exceptions.AllCabsBusyException;
import com.example.cabmanagement.exceptions.CabNotFoundException;
import com.example.cabmanagement.exceptions.CityNotFoundException;
import com.example.cabmanagement.exceptions.TripNotFoundException;
import com.example.cabmanagement.models.dto.RestCity;
import com.example.cabmanagement.models.dto.RestTrip;
import com.example.cabmanagement.models.service.City;
import com.example.cabmanagement.models.service.Trip;
import com.example.cabmanagement.services.BookingService;
import com.example.cabmanagement.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public RestTrip bookCab(@RequestBody RestCity city) throws CabNotFoundException, AllCabsBusyException, CityNotFoundException {
        Trip trip = bookingService.bookCabAndStartTrip(CopyUtil.deepCopy(city, City.class));
        return CopyUtil.deepCopy(trip, RestTrip.class);
    }

    @PutMapping("/completeBooking/{cabId}")
    public RestTrip completeBooking(@PathVariable("cabId") Long cabId) throws CabNotFoundException, TripNotFoundException {
        Trip trip = bookingService.endTrip(cabId);
        return CopyUtil.deepCopy(trip, RestTrip.class);
    }

    @GetMapping("/bookings/{cabId}")
    public List<RestTrip> getAllTripsForCab(@PathVariable("cabId") Long cabId) throws CabNotFoundException {
        return bookingService.getAllTripsForCab(cabId)
                .stream()
                .map(trip -> CopyUtil.deepCopy(trip, RestTrip.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/bookings")
    public List<RestTrip> getAllTrips() throws CabNotFoundException {
        return bookingService.getAllTripsForCab()
                .stream()
                .map(trip -> CopyUtil.deepCopy(trip, RestTrip.class))
                .collect(Collectors.toList());
    }
}
