package com.project.routeCheckpoint.persistance.models;

import java.math.BigDecimal;

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
@Table(name = "checkpoint")
public class Checkpoint {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_checkpoint", nullable = false)
	private int checkpointId;
	
	@Column(name = "length")
    private BigDecimal length;

    @Column(name = "latitude")
    private BigDecimal latitude;
    
    @Column(name="name")
    private String nameCheckpoint;

}
