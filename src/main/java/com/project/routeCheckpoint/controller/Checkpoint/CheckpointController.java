package com.project.routeCheckpoint.controller.Checkpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.routeCheckpoint.dto.RouteWithCheckpointsDTO;
import com.project.routeCheckpoint.persistance.models.Checkpoint;
import com.project.routeCheckpoint.persistance.models.Response;
import com.project.routeCheckpoint.services.Checkpoint.CheckpointServiceI;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class CheckpointController {
	
	@Autowired
	private CheckpointServiceI checkpointService;
	
	@PostMapping("/route")
	public ResponseEntity<Response<Checkpoint>> addRoute (@Valid @RequestBody RouteWithCheckpointsDTO routeWithCheckpointsDTO){
		return checkpointService.addRoute(routeWithCheckpointsDTO);
	}

	@DeleteMapping("/route/{routeId}")
    public ResponseEntity<Response<String>> deleteRoute(@PathVariable Integer routeId) {
        return checkpointService.deleteRoute(routeId);
    }

}
