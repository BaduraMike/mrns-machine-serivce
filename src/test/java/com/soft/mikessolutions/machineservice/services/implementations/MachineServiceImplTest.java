package com.soft.mikessolutions.machineservice.services.implementations;


import com.soft.mikessolutions.machineservice.entities.Machine;
import com.soft.mikessolutions.machineservice.exceptions.MachineNotFoundException;
import com.soft.mikessolutions.machineservice.services.MachineService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MachineServiceImplTest {

    @Autowired
    private MachineService machineService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldFindAllMachines() {
        Assert.assertFalse(machineService.findAll().isEmpty());
    }

    @Test
    public void shouldFindMachineByExistingId() {
        long existingId = machineService.findAll().size();
        Assert.assertNotNull(machineService.findById(existingId));
    }

    @Test
    public void shouldThrowMachineNotFoundExceptionForNonExisitingId() {
        Long nonExistingId = (long) (machineService.findAll().size() + 1);
        expectedException.expect(MachineNotFoundException.class);
        expectedException.expectMessage("Machine with {id} = " + nonExistingId + " is not found.");
        machineService.findById(nonExistingId);
    }

    @Test
    public void shouldSaveNewMachine() {
        //GIVEN
        int preSaveUserCount = machineService.findAll().size();
        Machine newMachine = new Machine();
        //WHEN
        machineService.save(newMachine);
        int postSaveUserCount = machineService.findAll().size();
        //THEN
        Assert.assertEquals(1, postSaveUserCount - preSaveUserCount);
    }

    @Test
    public void shouldDeleteUser() {
        //GIVEN
        Machine newMachine = new Machine();
        machineService.save(newMachine);
        int preDeleteUserCount = machineService.findAll().size();
        //WHEN
        machineService.delete(newMachine);
        int postDeleteUserCount = machineService.findAll().size();
        //THEN
        Assert.assertEquals(1, preDeleteUserCount - postDeleteUserCount);
    }

    @Test
    public void shouldDeleteUserByExistingId() {
        //GIVEN
        Machine newMachine = new Machine();
        machineService.save(newMachine);
        int preDeleteUserCount = machineService.findAll().size();
        //WHEN
        machineService.deleteById(newMachine.getIdentityNumber());
        int postDeleteUserCount = machineService.findAll().size();
        //THEN
        Assert.assertEquals(1, preDeleteUserCount - postDeleteUserCount);
    }

    @Test
    public void shouldPersistLocalDateAsAServiceDate() {
        //GIVEN
        long testMachineId = 1;
        LocalDate localDate = LocalDate.of(2020, 3, 23);
        Machine testMachine = machineService.findById(testMachineId);
        testMachine.setServiceDate(localDate);
        machineService.save(testMachine);
        //WHEN
        LocalDate machineServiceDate = machineService.findById(testMachineId).getServiceDate();
        //THEN
        Assert.assertEquals(localDate, machineServiceDate);
    }

}