package com.project.routeCheckpoint.persistance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.routeCheckpoint.persistance.models.Route;

public interface RouteRepository extends JpaRepository<Route, Integer>{
    Optional<Route> findByName(String name);
}
