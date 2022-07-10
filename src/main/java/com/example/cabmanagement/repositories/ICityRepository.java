package com.example.cabmanagement.repositories;

import com.example.cabmanagement.models.dao.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICityRepository extends JpaRepository<CityEntity, String> {
}
