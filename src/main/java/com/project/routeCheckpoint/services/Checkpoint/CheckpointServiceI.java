package com.project.routeCheckpoint.services.Checkpoint;

import org.springframework.http.ResponseEntity;

import com.project.routeCheckpoint.dto.CheckpointDTO;
import com.project.routeCheckpoint.dto.RouteWithCheckpointsDTO;
import com.project.routeCheckpoint.persistance.models.Checkpoint;
import com.project.routeCheckpoint.persistance.models.Response;

public interface CheckpointServiceI {
	
	ResponseEntity<Response<Checkpoint>> addRoute(RouteWithCheckpointsDTO routeWithCheckpointsDTO);
	public ResponseEntity<Response<String>> deleteRoute(Integer routeId);
	CheckpointDTO findCheckpointByID(int checkpointId);
	

}
