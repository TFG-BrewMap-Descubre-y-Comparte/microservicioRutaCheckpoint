package com.project.routeCheckpoint.controller.Route;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.routeCheckpoint.dto.RouteDTO;
import com.project.routeCheckpoint.persistance.models.Route;
import com.project.routeCheckpoint.services.Route.RouteServiceI;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class RouteController {
	
	@Autowired
	private RouteServiceI routeService;
	
	@GetMapping("/routes")
	public List<RouteDTO> getAllRoutes(){
		return routeService.routes();
	}
	
	@GetMapping("/routes/city/{nameCity}")
	public List<RouteDTO> getAllRoutesByCity(@PathVariable String nameCity){
		return routeService.findRoutesByCity(nameCity);
	}
	
	@GetMapping("/routes/id/{id}")
	public RouteDTO getRouteByID(@PathVariable int id) {
		return routeService.findRoutById(id);
	}

}
