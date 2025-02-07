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
import com.project.routeCheckpoint.dto.RouteWithCheckpointsDTO;
import com.project.routeCheckpoint.exceptions.ExceptionNotFoundCheckpoint;
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
	public ResponseEntity<Response<Checkpoint>> addRoute(RouteWithCheckpointsDTO routeWithCheckpointsDTO) {
		
		try {
			if (routeWithCheckpointsDTO == null) {
	            throw new ExceptionNotValidData("RouteDTO cannot be null.");
	        }

			 // Validar que los checkpoints no estén vacíos
	        if (routeWithCheckpointsDTO.getCheckpoints() == null || routeWithCheckpointsDTO.getCheckpoints().isEmpty()) {
	            throw new ExceptionNotValidData("Route must have at least one checkpoint.");
	        }
	        
	        String cityName = routeWithCheckpointsDTO.getCityName();
	        Optional<City> optionalCity = cityRepository.findByNameCity(cityName);
	        if (!optionalCity.isPresent()) {
	            throw new ExceptionNotFoundCity("City name not found.");
	        }
	        
	        City city = optionalCity.get();
	           
	        // Crear la entidad Route
	        Route route = new Route();
	        route.setName(routeWithCheckpointsDTO.getNameRoute());
	        route.setDescription(routeWithCheckpointsDTO.getDescriptionRoute());
	        route.setCity(city);
	        route.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
	        route.setUserId(1);
	        
	        // Obtener las coordenadas iniciales y finales de los checkpoints
	        CoordinatesDTO startCoordinates = routeWithCheckpointsDTO.getCheckpoints().get(0).getCoordinates();
	        CoordinatesDTO endCoordinates = routeWithCheckpointsDTO.getCheckpoints().get(routeWithCheckpointsDTO.getCheckpoints().size() - 1).getCoordinates();
 
	        // Llamada al servicio OpenRouteService para obtener la ruta
	        String routeData = openRouteService.getWalkingRoute(startCoordinates, endCoordinates).block();
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
		     
		     // Guardar los checkpoints y asociarlos a la ruta
	        List<Checkpoint> checkpointList = new ArrayList<>();
	        for (CheckpointDTO checkpointDTO : routeWithCheckpointsDTO.getCheckpoints()) {
	            Checkpoint checkpoint = new Checkpoint();
	            checkpoint.setNameCheckpoint(checkpointDTO.getNameCheckpoint());
	            checkpoint.setStartLatitude(checkpointDTO.getCoordinates().getStartLatitude());
	            checkpoint.setStartLongitude(checkpointDTO.getCoordinates().getStartLongitude());
	            checkpoint.setEndLatitude(checkpointDTO.getCoordinates().getEndLatitude());
	            checkpoint.setEndLongitude(checkpointDTO.getCoordinates().getEndLongitude());

	            // Guardar el checkpoint y agregarlo a la lista de la ruta
	            checkpoint = checkpointRepository.save(checkpoint);
	            checkpointList.add(checkpoint);
	        }
		     	
	        // Asociar los checkpoints a la ruta
	        route.setCheckpoints(checkpointList);

	        // Guardar la ruta con los checkpoints
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


	@Override
	public CheckpointDTO findCheckpointByID(int checkpointId) {
		Optional<Checkpoint> optionalCheckpoint = checkpointRepository.findById(checkpointId);
		
		if(!optionalCheckpoint.isPresent()) {
			throw new ExceptionNotFoundCheckpoint("The checkpoint not found");
		}
		
		Checkpoint checkpoint = optionalCheckpoint.get();
		
		CheckpointDTO checkpointDTO = new CheckpointDTO(checkpoint);	
		return checkpointDTO;
	}

}
