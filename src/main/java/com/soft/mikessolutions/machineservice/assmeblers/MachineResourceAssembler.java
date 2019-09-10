package com.soft.mikessolutions.machineservice.assmeblers;

import com.soft.mikessolutions.machineservice.controllers.MachineController;
import com.soft.mikessolutions.machineservice.entities.Machine;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class MachineResourceAssembler implements ResourceAssembler<Machine, Resource<Machine>> {
    @Override
    public Resource<Machine> toResource(Machine machine) {
        return new Resource<>(machine,
                linkTo(methodOn(MachineController.class).one(machine.getId())).withSelfRel(),
                linkTo(methodOn(MachineController.class).all()).withRel("machines"));
    }
}
