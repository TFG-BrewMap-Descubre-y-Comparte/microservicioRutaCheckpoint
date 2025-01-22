package com.project.routeCheckpoint.services.Route;

import java.util.List;

import com.project.routeCheckpoint.dto.RouteDTO;


public interface RouteServiceI {
	
	List<RouteDTO>routes();
	List<RouteDTO> findRoutesByCity(String cityName);


}
