package com.soft.mikessolutions.machineservice.services.implementations;

import com.soft.mikessolutions.machineservice.entities.Machine;
import com.soft.mikessolutions.machineservice.exceptions.MachineNotFoundException;
import com.soft.mikessolutions.machineservice.repositories.MachineRepository;
import com.soft.mikessolutions.machineservice.services.MachineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;

    MachineServiceImpl(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @Override
    public List<Machine> findAll() {
        return machineRepository.findAll();
    }

    @Override
    public Machine findById(Long id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new MachineNotFoundException(id));
    }

    @Override
    public Machine save(Machine machine) {
        return machineRepository.save(machine);
    }

    @Override
    public void delete(Machine machine) {
        machineRepository.delete(machine);
    }

    @Override
    public void deleteById(Long id) {
        machineRepository.deleteById(findById(id).getId());
    }
}
