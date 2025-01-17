package com.project.routeCheckpoint.persistance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.routeCheckpoint.persistance.models.City;

public interface CityRepository  extends JpaRepository<City, Integer>{
	
	Optional<City> findByNameCity(String nameCity);

}
