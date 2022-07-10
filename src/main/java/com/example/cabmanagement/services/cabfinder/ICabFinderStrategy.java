package com.example.cabmanagement.services.cabfinder;

import com.example.cabmanagement.exceptions.AllCabsBusyException;
import com.example.cabmanagement.exceptions.CabNotFoundException;
import com.example.cabmanagement.exceptions.CityNotFoundException;
import com.example.cabmanagement.models.service.Cab;
import com.example.cabmanagement.models.service.City;

public interface ICabFinderStrategy {
    Cab findCab(City city) throws AllCabsBusyException, CabNotFoundException, CityNotFoundException;
}
