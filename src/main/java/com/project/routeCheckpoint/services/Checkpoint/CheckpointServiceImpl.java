package com.project.routeCheckpoint.services.Checkpoint;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.routeCheckpoint.dto.CheckpointDTO;
import com.project.routeCheckpoint.dto.CoordinatesDTO;
import com.project.routeCheckpoint.exceptions.ExceptionNotFoundCity;
import com.project.routeCheckpoint.exceptions.ExceptionNotValidData;
import com.project.routeCheckpoint.exceptions.ExceptionRoutNotFound;
import com.project.routeCheckpoint.persistance.models.Checkpoint;
import com.project.routeCheckpoint.persistance.models.City;
import com.project.routeCheckpoint.persistance.models.Response;
import com.project.routeCheckpoint.persistance.models.Route;
import com.project.routeCheckpoint.persistance.repository.CheckpointRepository;
import com.project.routeCheckpoint.persistance.repository.CityRepository;
import com.project.routeCheckpoint.persistance.repository.RouteRepository;
import com.project.routeCheckpoint.services.OpenRouteService.OpenRouteService;

@Service
public class CheckpointServiceImpl implements CheckpointServiceI{
	
	@Autowired
	private CheckpointRepository checkpointRepository;
	
	@Autowired
	private RouteRepository routeRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
    private OpenRouteService openRouteService;

	@Override
	public ResponseEntity<Response<Checkpoint>> addRoute(CoordinatesDTO coordinatesDTO) {
		
		try {
			 if (coordinatesDTO == null) {
		            throw new ExceptionNotValidData("CoordinatesDTO cannot be null.");
	        }

	        if (coordinatesDTO.getCheckpoints() == null || coordinatesDTO.getCheckpoints().isEmpty()) {
	            throw new ExceptionNotValidData("Checkpoints cannot be null or empty.");
	        }

	        for (CheckpointDTO checkpointDTO : coordinatesDTO.getCheckpoints()) {
	            if (checkpointDTO.getCoordinates() == null) {
	                throw new ExceptionNotValidData("Coordinates cannot be null for checkpoint");
	            }

	            if (checkpointDTO.getCoordinates().getStartLatitude() == null || checkpointDTO.getCoordinates().getStartLongitude() == null ||
	                checkpointDTO.getCoordinates().getEndLatitude() == null || checkpointDTO.getCoordinates().getEndLongitude() == null) {
	                throw new ExceptionNotValidData("Coordinates are incomplete.");
	            }
	        }	        
	        
	        Route route = new Route();   
        
	        route.setName(coordinatesDTO.getNameRoute());
	        route.setDescription(coordinatesDTO.getDescriptionRoute());
	        route.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
	        route.setUserId(1);
	        
	        Optional<City> cityOptional = cityRepository.findByNameCity(coordinatesDTO.getCityName());
	        
	        if(!cityOptional.isPresent()) {
	        	throw new ExceptionNotFoundCity("City not found");
	        }
	        
	        City city = cityOptional.get();
	        route.setCity(city);

	        
	        // Llamada al servicio OpenRouteService para obtener la ruta
	        String routeData = openRouteService.getWalkingRoute(coordinatesDTO).block();
	        //System.out.println(routeData);
	        
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode rootNode = mapper.readTree(routeData);
	        JsonNode summaryNode = rootNode.path("features").get(0).path("properties").path("summary");

	        double distance = summaryNode.path("distance").asDouble();
	        double duration = summaryNode.path("duration").asDouble();

	        float distanceInKm = (float) (distance / 1000); // Convertir metros a kilómetros

		     // Convertir duración de segundos a minutos y segundos
		     int minutes = (int) (duration / 60);
		     int seconds = (int) (duration % 60);
	
		     // Crear LocalTime con la duración
		     LocalTime durationTime = LocalTime.of(minutes, seconds);
	
		     // Asignar valores a la ruta
		     route.setDistance(distanceInKm); 
		     route.setDuration(durationTime);
	        
	        // Añadir los checkpoints a la lista de la ruta
	        List<Checkpoint> checkpointList = new ArrayList<>();
	        for (CheckpointDTO checkpointDTO : coordinatesDTO.getCheckpoints()) {
	            Checkpoint checkpoint = new Checkpoint();
	            checkpoint.setNameCheckpoint(checkpointDTO.getNameCheckpoint());
	            checkpoint.setStartLatitude(checkpointDTO.getCoordinates().getStartLatitude());
	            checkpoint.setStartLongitude(checkpointDTO.getCoordinates().getStartLongitude());
	            checkpoint.setEndLatitude(checkpointDTO.getCoordinates().getEndLatitude());
	            checkpoint.setEndLongitude(checkpointDTO.getCoordinates().getEndLongitude());

	            // Guardar el checkpoint en la base de datos
	            checkpointRepository.save(checkpoint);

	            // Agregar el checkpoint a la lista de la ruta
	            checkpointList.add(checkpoint);
	        }
	        
	        route.setCheckpoints(checkpointList);
	        
	        // Guardar la ruta en el repositorio
	        routeRepository.save(route);

	        // Crear la respuesta
	        Response<Checkpoint> response = new Response<>(HttpStatus.CREATED, "Route saved correctly");
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    } catch (Exception e) {
	        // Manejar excepciones
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage()));
	    }
	}


	@Override
	public ResponseEntity<Response<String>> deleteRoute(Integer routeId) {
		try {
			// Buscar la ruta por ID
			Optional<Route> routeOptional = routeRepository.findById(routeId);

			if (!routeOptional.isPresent()) {
				throw new ExceptionRoutNotFound ("Route not found");
			}

			Route route = routeOptional.get();

			// Desvincular los checkpoints de la ruta
			for (Checkpoint checkpoint : route.getCheckpoints()) {
				checkpoint.getRoutes().remove(route);
			}

			// Eliminar la ruta de la base de datos
			routeRepository.delete(route);

			Response<String> response = new Response<>(HttpStatus.OK, "Route deleted correctly");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage()));
		}
	}

	


}
