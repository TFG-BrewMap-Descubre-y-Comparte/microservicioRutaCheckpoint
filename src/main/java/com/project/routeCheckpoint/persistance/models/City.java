package com.project.routeCheckpoint.persistance.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "city")
public class City {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_city", nullable = false)
	private int cityId;
	
	@Column(name = "name")
	private String nameCity;
	
	

}
