package com.example.mySpringBoot.repositories;

import com.example.mySpringBoot.models.Drone;
import com.example.mySpringBoot.models.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findByState(DroneState state);
}
