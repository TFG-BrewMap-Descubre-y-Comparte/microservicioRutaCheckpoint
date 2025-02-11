package com.project.routeCheckpoint.services.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.project.routeCheckpoint.dto.AudioguiaDTO;
import com.project.routeCheckpoint.dto.CheckpointDTO;
import com.project.routeCheckpoint.dto.RouteDTO;
import com.project.routeCheckpoint.exceptions.ExceptionNotFoundCity;
import com.project.routeCheckpoint.exceptions.ExceptionRoutNotFound;
import com.project.routeCheckpoint.persistance.models.Checkpoint;
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
	
	@Autowired
    private WebClient webClient;

	@Override
	public List<RouteDTO> routes() {
		
		List<Route> routes = routeRepository.findAll();
		List<RouteDTO> listRoutesDTO = new ArrayList<>();
		
		for(Route route : routes) {
			RouteDTO routeDTO = new RouteDTO(route);
			
			 // Añadimos los checkpoints con sus audioguías
	        routeDTO.setCheckpointHasRoute(getCheckpointsWithAudioguide(route.getCheckpoints()));
	        
	        listRoutesDTO.add(routeDTO);
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
			RouteDTO routeDTO = new RouteDTO(route);
			
			routeDTO.setCheckpointHasRoute(getCheckpointsWithAudioguide(route.getCheckpoints()));
			listRoutesDTO.add(routeDTO);
		}
		
		return listRoutesDTO;
	}
	
	// Método para obtener checkpoints con audioguías
	public List<CheckpointDTO> getCheckpointsWithAudioguide(List<Checkpoint> checkpoints) {
	    List<CheckpointDTO> checkpointDTOList = new ArrayList<>();

	    for (Checkpoint checkpoint : checkpoints) {
	        CheckpointDTO checkpointDTO = new CheckpointDTO(checkpoint);

	        try {
	            // Llamada al microservicio de audioguías
	            List<AudioguiaDTO> audioguias = webClient
	                    .get()
	                    .uri("/audioguides/checkpoint/{id_checkpoint}", checkpoint.getCheckpointId())
	                    .retrieve()
	                    .bodyToFlux(AudioguiaDTO.class)
	                    .collectList()
	                    .block();

	            if (audioguias != null && !audioguias.isEmpty()) {
	                AudioguiaDTO audioguia = audioguias.get(0);

	                // Asignamos la audioguía directamente
	                checkpointDTO.setAudioguiaDTO(audioguia);
	            } else {
	                checkpointDTO.setAudioguiaDTO(null); // No hay audioguía asociada
	            }
	        } catch (Exception e) {
	            // Log de error para depuración
	            System.err.println("Error getting the audioguide for the checkpoint " + checkpoint.getCheckpointId());
	            e.printStackTrace();
	            checkpointDTO.setAudioguiaDTO(null); // En caso de error, no asignamos audioguía
	        }

	        checkpointDTOList.add(checkpointDTO);
	    }

	    return checkpointDTOList;
	}




	@Override
	public RouteDTO findRoutById(int routeId) {
		
		Optional<Route> optionalRoute = routeRepository.findById(routeId);
		
		if(!optionalRoute.isPresent()) {
			throw new ExceptionRoutNotFound ("The route with the name of city not found");
		}
		Route route = optionalRoute.get();
		RouteDTO routedto = new RouteDTO(route);
		
		routedto.setCheckpointHasRoute(getCheckpointsWithAudioguide(route.getCheckpoints()));
		
		return routedto;
	}

}
