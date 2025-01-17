package com.project.routeCheckpoint.dto;

import java.math.BigDecimal;
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
	private BigDecimal startLatitude;
	private BigDecimal startLongitude;
	private BigDecimal endLatitude;
	private BigDecimal endLongitude;
	
	private List<RouteDTO> routesHasCheckpoint = new ArrayList<>();
	
	public CheckpointDTO (Checkpoint checkpoint) {
		this.idCheckpoint = checkpoint.getCheckpointId();
		this.nameCheckpoint = checkpoint.getNameCheckpoint();
		this.startLatitude = checkpoint.getStartLatitude();
		this.endLatitude = checkpoint.getEndLatitude();
		this.startLongitude = checkpoint.getStartLongitude();
		this.endLongitude = checkpoint.getEndLongitude();
		
		for(Route routeCheckpoints : checkpoint.getRoutes()) {
			RouteDTO route = new RouteDTO(routeCheckpoints);
			routesHasCheckpoint.add(route);
		}
	}
	

}
