package com.soft.mikessolutions.machineservice.entities;

import com.soft.mikessolutions.machineservice.entities.enums.MachineType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
public class Machine extends BaseEntity {

    private String brand;
    private String model;
    private String serialNumber;
    private String udtNumber;
    @Enumerated(EnumType.STRING)
    private MachineType machineType;
    private String prodYear;
    private LocalDate serviceDate;

    public Machine() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getUdtNumber() {
        return udtNumber;
    }

    public void setUdtNumber(String udtNumber) {
        this.udtNumber = udtNumber;
    }

    public MachineType getMachineType() {
        return machineType;
    }

    public void setMachineType(MachineType machineType) {
        this.machineType = machineType;
    }

    public String getProdYear() {
        return prodYear;
    }

    public void setProdYear(String prodYear) {
        this.prodYear = prodYear;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }
}

