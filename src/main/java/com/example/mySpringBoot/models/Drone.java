package com.example.mySpringBoot.models;

import javax.persistence.*;
import java.util.List;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DroneModel getModel() {
        return model;
    }

    public void setModel(DroneModel model) {
        this.model = model;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public DroneState getState() {
        return state;
    }

    public void setState(DroneState state) {
        this.state = state;
    }

    public List<Medication> getMedications() {
        return null;
    }
}
