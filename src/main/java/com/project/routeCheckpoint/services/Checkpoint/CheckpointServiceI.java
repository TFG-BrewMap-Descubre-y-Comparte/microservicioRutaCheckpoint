package com.project.routeCheckpoint.services.Checkpoint;

import org.springframework.http.ResponseEntity;

import com.project.routeCheckpoint.dto.CoordinatesDTO;
import com.project.routeCheckpoint.persistance.models.Checkpoint;
import com.project.routeCheckpoint.persistance.models.Response;

public interface CheckpointServiceI {
	
	ResponseEntity<Response<Checkpoint>> addRoute(CoordinatesDTO coordinatesDTO);
	public ResponseEntity<Response<String>> deleteRoute(Integer routeId);

}
