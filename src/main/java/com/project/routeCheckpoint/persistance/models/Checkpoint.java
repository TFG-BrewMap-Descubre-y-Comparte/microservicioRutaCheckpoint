package com.project.routeCheckpoint.persistance.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_checkpoint", nullable = false)
	private int checkpointId;
	
	@Column(name = "length")
    @Digits(integer = 3, fraction = 6)
    private BigDecimal length;

    @Column(name = "latitude")
    @Digits(integer = 3, fraction = 6)
    private BigDecimal latitude;
    
    @Column(name="name")
    @Size(min = 2, max = 100, message = "La descripcion debe tener entre 2 y 100 caracteres.")
    private String nameCheckpoint;

}
