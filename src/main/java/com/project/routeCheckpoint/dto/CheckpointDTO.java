package com.project.routeCheckpoint.dto;

import java.util.ArrayList;
import java.util.List;

import com.project.routeCheckpoint.persistance.models.Checkpoint;
import com.project.routeCheckpoint.persistance.models.Route;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CheckpointDTO {
	
	private int idCheckpoint;
	private String nameCheckpoint;
	private CoordinatesDTO coordinates;
	
	private List<RouteDTO> routesHasCheckpoint = new ArrayList<>();
	
	public CheckpointDTO (Checkpoint checkpoint) {
		this.idCheckpoint = checkpoint.getCheckpointId();
		this.nameCheckpoint = checkpoint.getNameCheckpoint();
		this.coordinates = new CoordinatesDTO();
        this.coordinates.setStartLatitude(checkpoint.getStartLatitude());
        this.coordinates.setStartLongitude(checkpoint.getStartLongitude());
        this.coordinates.setEndLatitude(checkpoint.getEndLatitude());
        this.coordinates.setEndLongitude(checkpoint.getEndLongitude());
		
		for(Route routeCheckpoints : checkpoint.getRoutes()) {
			RouteDTO route = new RouteDTO(routeCheckpoints);
			routesHasCheckpoint.add(route);
		}
	}
	

}
