package com.project.routeCheckpoint.services.Route;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.routeCheckpoint.dto.RouteDTO;
import com.project.routeCheckpoint.persistance.models.Route;
import com.project.routeCheckpoint.persistance.repository.RouteRepository;

@Service
public class RouteServiceImpl implements RouteServiceI{
	
	@Autowired
	private RouteRepository routeRepository;

	@Override
	public List<RouteDTO> routes() {
		
		List<Route> routes = routeRepository.findAll();
		List<RouteDTO> listRoutesDTO = new ArrayList<>();
		
		for(Route route : routes) {
			listRoutesDTO.add(new RouteDTO(route));
		}
		 
		return listRoutesDTO;
	}

}
