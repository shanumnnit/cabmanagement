package com.example.cabmanagement.repositories;

import com.example.cabmanagement.models.common.TripStatus;
import com.example.cabmanagement.models.dao.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITripRepository extends JpaRepository<TripEntity, Long> {

    //find the latest trip for cab where end time is not set i.e. trip is in progress
    TripEntity findTop1ByCabIdAndTripEndTimeIsNullOrderByTripEndTimeDesc(Long cabId);

    //find the last ended trip for a cab
    TripEntity findTop1ByCabIdAndTripStatusEqualsOrderByTripEndTimeDesc(Long cabId, TripStatus tripStatus);

    List<TripEntity> findAllByCabIdOrderByTripStartTimeAsc(Long cabId);
}
