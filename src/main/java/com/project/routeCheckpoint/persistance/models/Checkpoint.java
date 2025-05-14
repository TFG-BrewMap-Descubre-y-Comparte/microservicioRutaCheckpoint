package com.project.routeCheckpoint.persistance.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "checkpoint")
public class Checkpoint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_checkpoint", nullable = false)
	private int checkpointId;
	
	@Column(name="name")
	@Size(min = 2, max = 100, message = "La descripcion debe tener entre 2 y 100 caracteres.")
	private String nameCheckpoint;
	
	@Column(name = "start_latitude")
	@Digits(integer = 3, fraction = 6)
	private BigDecimal startLatitude;

	@Column(name = "start_longitude")
	@Digits(integer = 3, fraction = 6)
	private BigDecimal startLongitude;

	@Column(name = "end_latitude")
	@Digits(integer = 3, fraction = 6)
	private BigDecimal endLatitude;

	@Column(name = "end_longitude")
	@Digits(integer = 3, fraction = 6)
	private BigDecimal endLongitude;
	
	@Column(name = "schedule", nullable = true)
	private String schedule;


    
    @ManyToMany(mappedBy = "checkpoints") 
    private List<Route> routes = new ArrayList<>();
    

}
