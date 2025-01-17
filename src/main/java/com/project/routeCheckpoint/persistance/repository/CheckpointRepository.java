package com.project.routeCheckpoint.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.routeCheckpoint.persistance.models.Checkpoint;

public interface CheckpointRepository extends JpaRepository<Checkpoint, Integer>{

}
