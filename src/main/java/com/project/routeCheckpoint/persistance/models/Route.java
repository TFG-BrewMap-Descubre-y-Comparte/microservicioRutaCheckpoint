package com.project.routeCheckpoint.persistance.models;

import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Size;

public class Route {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_route", nullable = false)
	private int routeId;
	
	@Column(name = "name", unique = true)
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String name;
	
	@Column(name = "description")
    private String description;

    @Column(name = "duration")
    private LocalTime duration;
    
    @Column(name = "distance")
    private float distance;

    @Column(name = "created_date")
    private Date createdDate;
    
    @Column(name = "id_user", nullable = false)
    private int userId;
    
    @Column(name = "id_city", nullable = false)
    private int cityId;

}
