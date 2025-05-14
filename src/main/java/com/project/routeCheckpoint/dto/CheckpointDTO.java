package com.project.routeCheckpoint.dto;


import com.project.routeCheckpoint.persistance.models.Checkpoint;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CheckpointDTO {
	
	private int idCheckpoint;
	private String nameCheckpoint;
	private CoordinatesDTO coordinates;
	private String schedule;
	private AudioguiaDTO audioguiaDTO;
	
	
	public CheckpointDTO (Checkpoint checkpoint) {
		this.idCheckpoint = checkpoint.getCheckpointId();
		this.nameCheckpoint = checkpoint.getNameCheckpoint();
		this.coordinates = new CoordinatesDTO();
        this.coordinates.setStartLatitude(checkpoint.getStartLatitude());
        this.coordinates.setStartLongitude(checkpoint.getStartLongitude());
        this.coordinates.setEndLatitude(checkpoint.getEndLatitude());
        this.coordinates.setEndLongitude(checkpoint.getEndLongitude());
        this.schedule = checkpoint.getSchedule();
		
	}
	

}
