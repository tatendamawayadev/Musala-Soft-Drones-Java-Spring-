# Musala-Soft-Drones-Java-Spring-
Musala Soft Drones Java Spring Project

I'll provide you with an example implementation of the Java Spring REST API service to manage drones and medication items based on the requirements you provided. 

First, let's create the necessary data models: Drone and Medication.

Drone.java:
java
Copy code
import javax.persistence.*;

@Entity
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModel model;

    private double weightLimit;

    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private DroneState state;

    // Constructors, getters, and setters
}
DroneModel.java:
java
Copy code
public enum DroneModel {
    Lightweight,
    Middleweight,
    Cruiserweight,
    Heavyweight
}
DroneState.java:
java
Copy code
public enum DroneState {
    IDLE,
    LOADING,
    LOADED,
    DELIVERING,
    DELIVERED,
    RETURNING
}
Medication.java:
java
Copy code
import javax.persistence.*;

@Entity
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double weight;

    private String code;

    private String image;

    // Constructors, getters, and setters
}
Next, let's implement the REST API endpoints for managing drones and medication items.

DroneController.java:
java
Copy code
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
DroneService.java:
java
Copy code
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
DroneRepository.java:
java
Copy code
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findByState(DroneState state);
}
DroneNotFoundException.java:
java
Copy code
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DroneNotFoundException extends RuntimeException {
    public DroneNotFoundException() {
        super("Drone not found");
    }
}
