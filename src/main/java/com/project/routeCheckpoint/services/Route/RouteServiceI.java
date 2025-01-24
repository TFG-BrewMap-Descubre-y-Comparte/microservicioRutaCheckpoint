package com.project.routeCheckpoint.services.Route;

import java.util.List;

import com.project.routeCheckpoint.dto.RouteDTO;
import com.project.routeCheckpoint.persistance.models.Route;


public interface RouteServiceI {
	
	List<RouteDTO>routes();
	List<RouteDTO> findRoutesByCity(String cityName);
	RouteDTO findRoutById(int routeId);


}
