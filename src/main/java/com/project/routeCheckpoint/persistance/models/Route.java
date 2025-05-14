package com.project.routeCheckpoint.persistance.models;

import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "route")
public class Route {
	
	@Id
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
    
    @Column(name = "category")
    private String category;
    
    //Relación con User microservicio
    @Column(name = "id_user", nullable = false)
    private int userId;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_city", referencedColumnName = "id_city", nullable = false)
    private City city;
    
    @ManyToMany
    @JoinTable(name="route_checkpoint", joinColumns= {@JoinColumn(name="id_route")}, inverseJoinColumns={@JoinColumn(name="id_checkpoint")})
    private List<Checkpoint> checkpoints = new ArrayList<>();

}
