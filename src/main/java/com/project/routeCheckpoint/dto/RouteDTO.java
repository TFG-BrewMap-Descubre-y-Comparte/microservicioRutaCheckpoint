package com.project.routeCheckpoint.dto;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.project.routeCheckpoint.persistance.models.Checkpoint;
import com.project.routeCheckpoint.persistance.models.Route;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RouteDTO {
	
	private int idRoute;
	private String nameRoute;
    private String description;
    private LocalTime duration;
    private float distance;
    private Date createdDate;
    private int idUser;
    private CityDTO city;
    
    private List<CheckpointDTO> checkpointHasRoute = new ArrayList<>();
    
    public RouteDTO (Route route) {
    	this.idRoute = route.getRouteId();
    	this.nameRoute = route.getName();
    	this.description = route.getDescription();
    	this.duration = route.getDuration();
    	this.distance = route.getDistance();
    	this.createdDate = route.getCreatedDate();
    	this.idUser = route.getUserId();
    	this.city = new CityDTO(route.getCity());
    	
    	for(Checkpoint checkpointRoutes : route.getCheckpoints()) {
    		CheckpointDTO checkpoint = new CheckpointDTO(checkpointRoutes);
    		checkpointHasRoute.add(checkpoint);
    	}	
    }

}
