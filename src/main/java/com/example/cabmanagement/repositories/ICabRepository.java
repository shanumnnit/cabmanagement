package com.example.cabmanagement.repositories;

import com.example.cabmanagement.models.dao.CabEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICabRepository extends JpaRepository<CabEntity, Long> {

    List<CabEntity> findCabEntitiesByCityCode(String cityCode);
}
