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
@Table(name = "country")
public class Country {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_country", nullable = false)
	private int countryId;
	
	@Column(name="name", nullable = false)
	private String name;

}
