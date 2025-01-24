package com.project.routeCheckpoint.services.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.routeCheckpoint.dto.RouteDTO;
import com.project.routeCheckpoint.exceptions.ExceptionNotFoundCity;
import com.project.routeCheckpoint.exceptions.ExceptionRoutNotFound;
import com.project.routeCheckpoint.persistance.models.City;
import com.project.routeCheckpoint.persistance.models.Route;
import com.project.routeCheckpoint.persistance.repository.CityRepository;
import com.project.routeCheckpoint.persistance.repository.RouteRepository;

@Service
public class RouteServiceImpl implements RouteServiceI{
	
	@Autowired
	private RouteRepository routeRepository;
	
	@Autowired
	private CityRepository cityRepository;

	@Override
	public List<RouteDTO> routes() {
		
		List<Route> routes = routeRepository.findAll();
		List<RouteDTO> listRoutesDTO = new ArrayList<>();
		
		for(Route route : routes) {
			listRoutesDTO.add(new RouteDTO(route));
		}
		 
		return listRoutesDTO;
	}

	@Override
	public List<RouteDTO> findRoutesByCity(String cityName) {
		Optional<City> optionalCity = cityRepository.findByNameCity(cityName);
		
		if(!optionalCity.isPresent()) {
			throw new ExceptionNotFoundCity ("The route with the name of city not found");
		}
		
		City city = optionalCity.get();
		
		List<Route> routes = routeRepository.findByCity(city);
		
		List<RouteDTO> listRoutesDTO = new ArrayList<>();
		
		for(Route route : routes){
			listRoutesDTO.add(new RouteDTO(route));
		}
		
		return listRoutesDTO;
	}

	@Override
	public RouteDTO findRoutById(int routeId) {
		
		Optional<Route> optionalRoute = routeRepository.findById(routeId);
		
		if(!optionalRoute.isPresent()) {
			throw new ExceptionRoutNotFound ("The route with the name of city not found");
		}
		Route route = optionalRoute.get();
		RouteDTO routedto = new RouteDTO(route);
		
		return routedto;
	}

}
