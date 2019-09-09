package com.soft.mikessolutions.machineservice.exceptions;

public class MachineNotFoundException extends RuntimeException {
    public MachineNotFoundException(Long id) {
        super("Machine with {id} = " + id + " is not found.");
    }
}