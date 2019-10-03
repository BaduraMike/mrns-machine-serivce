package com.soft.mikessolutions.machineservice.controllers;

import com.soft.mikessolutions.machineservice.assmeblers.MachineResourceAssembler;
import com.soft.mikessolutions.machineservice.entities.Machine;
import com.soft.mikessolutions.machineservice.services.MachineService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class MachineController {
    private final MachineService machineService;
    private final MachineResourceAssembler machineAssembler;

    MachineController(MachineService machineService,
                      MachineResourceAssembler machineAssembler) {
        this.machineService = machineService;
        this.machineAssembler = machineAssembler;
    }

    @GetMapping("/machines")
    public Resources<Resource<Machine>> all() {
        List<Resource<Machine>> machines = machineService.findAll().stream()
                .map(machineAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(machines,
                linkTo(methodOn(MachineController.class).all()).withRel("machines"));
    }

    @GetMapping("/machines/{id:\\d+}")
    public Resource<Machine> one(@PathVariable Long id) {
        Machine machine = machineService.findById(id);

        return machineAssembler.toResource(machine);
    }

    @GetMapping(value = "/machines/serialNumber/{serialNumber}")
    public Resource<Machine> getBySerialNumber(@PathVariable String serialNumber) {
        Machine machine = machineService.findBySerialNumber(serialNumber);

        return machineAssembler.toResource(machine);
    }

    @PostMapping("/machines")
    public ResponseEntity<?> newMachine(@RequestBody Machine newMachine) throws URISyntaxException {
        Resource<Machine> resource = machineAssembler.toResource(machineService.save(newMachine));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/machines/{id}")
    public ResponseEntity<?> replaceMachine(@RequestBody Machine newMachine,
                                            @PathVariable Long id) {
        Machine machine = machineService.findById(id);
        machine.setSerialNumber(newMachine.getSerialNumber());
        machine.setUdtNumber(newMachine.getUdtNumber());
        machine.setServiceDate(newMachine.getServiceDate());
        machine.setModel(newMachine.getModel());
        machine.setBrand(newMachine.getBrand());
        machine.setProdYear(newMachine.getProdYear());
        machine.setMachineType(newMachine.getMachineType());
        machineService.save(machine);

        Resource<Machine> resource = machineAssembler.toResource(machine);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(resource);
    }

    @DeleteMapping("/machines/{id}")
    public ResponseEntity<?> deleteMachine(@PathVariable Long id) {
        machineService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
