package com.example.mySpringBoot.controllers;

import com.example.mySpringBoot.models.Drone;
import com.example.mySpringBoot.models.Medication;
import com.example.mySpringBoot.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drones")
public class DroneController {

    private final DroneService droneService;

    @Autowired
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping
    public ResponseEntity<Drone> registerDrone(@RequestBody Drone drone) {
        Drone registeredDrone = droneService.registerDrone(drone);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredDrone);
    }

    @PostMapping("/{droneId}/medications")
    public ResponseEntity<Drone> loadMedications(
            @PathVariable Long droneId,
            @RequestBody List<Medication> medications
    ) {
        Drone drone = droneService.loadMedications(droneId, medications);
        return ResponseEntity.ok(drone);
    }

    @GetMapping("/{droneId}/medications")
    public ResponseEntity<List<Medication>> getLoadedMedications(@PathVariable Long droneId) {
        List<Medication> medications = droneService.getLoadedMedications(droneId);
        return ResponseEntity.ok(medications);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Drone>> getAvailableDrones() {
        List<Drone> availableDrones = droneService.getAvailableDrones();
        return ResponseEntity.ok(availableDrones);
    }

    @GetMapping("/{droneId}/battery")
    public ResponseEntity<Integer> getDroneBatteryLevel(@PathVariable Long droneId) {
        int batteryLevel = droneService.getDroneBatteryLevel(droneId);
        return ResponseEntity.ok(batteryLevel);
    }
}
