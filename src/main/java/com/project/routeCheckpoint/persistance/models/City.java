package com.project.routeCheckpoint.persistance.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "city")
public class City {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_city", nullable = false)
	private int cityId;
	
	@Column(name = "name", nullable = false)
	private String nameCity;
	
	@ManyToOne
    @JoinColumn(name = "id_country", referencedColumnName = "id_country", nullable = false)
    private Country country;
	
	

}
