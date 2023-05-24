package com.example.mySpringBoot.services;

import com.example.mySpringBoot.models.Drone;
import com.example.mySpringBoot.models.DroneState;
import com.example.mySpringBoot.models.Medication;
import com.example.mySpringBoot.exceptions.DroneNotFoundException;
import com.example.mySpringBoot.repositories.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneService {

    private final DroneRepository droneRepository;

    @Autowired
    public DroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    public Drone registerDrone(Drone drone) {
        // Perform validations and register the drone in the database
        return droneRepository.save(drone);
    }

    public Drone loadMedications(Long droneId, List<Medication> medications) {
        // Load medications onto the drone and update its state in the database
        Drone drone = droneRepository.findById(droneId).orElseThrow(DroneNotFoundException::new);
        // Perform weight limit and battery level checks here
        // ...
        drone.setState(DroneState.LOADING);
        // Update other drone attributes
        // ...
        return droneRepository.save(drone);
    }

    public List<Medication> getLoadedMedications(Long droneId) {
        // Retrieve the loaded medications for the given drone from the database
        Drone drone = droneRepository.findById(droneId).orElseThrow(DroneNotFoundException::new);
        return drone.getMedications();
    }

    public List<Drone> getAvailableDrones() {
        // Retrieve the available drones from the database
        return droneRepository.findByState(DroneState.IDLE);
    }

    public int getDroneBatteryLevel(Long droneId) {
        // Retrieve the battery level for the given drone from the database
        Drone drone = droneRepository.findById(droneId).orElseThrow(DroneNotFoundException::new);
        return drone.getBatteryCapacity();
    }

    // Periodic task to check drone battery levels and create audit logs
    @Scheduled(fixedRate = 60000) // Runs every minute
    public void checkDroneBatteryLevels() {
        // Retrieve all drones from the database and check their battery levels
        List<Drone> drones = droneRepository.findAll();
        for (Drone drone : drones) {
            // Check battery level and create audit log
            // ...
        }
    }
}
