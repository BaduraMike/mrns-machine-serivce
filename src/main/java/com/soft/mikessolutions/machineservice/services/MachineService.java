package com.soft.mikessolutions.machineservice.services;

import com.soft.mikessolutions.machineservice.entities.Machine;
import org.springframework.stereotype.Service;

@Service
public interface MachineService extends CrudService<Machine, Long> {
}
