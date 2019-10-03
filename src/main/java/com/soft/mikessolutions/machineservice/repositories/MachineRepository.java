package com.soft.mikessolutions.machineservice.repositories;

import com.soft.mikessolutions.machineservice.entities.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    Machine findBySerialNumber(String serialNumber);
}
