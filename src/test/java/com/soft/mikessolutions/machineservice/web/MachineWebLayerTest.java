package com.soft.mikessolutions.machineservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.soft.mikessolutions.machineservice.entities.Machine;
import com.soft.mikessolutions.machineservice.services.MachineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MachineWebLayerTest {
    private final String BASE_URL = "/machines";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MachineService machineService;

    @Test
    public void getAllMachinesShouldReturnHttpStatus200Ok() throws Exception {
        this.mockMvc.perform(get(BASE_URL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getMachineByExistingIdShouldReturnHttpStatus200Ok() throws Exception {
        long id = machineService.findAll().size();
        String urlToExistingMachine = BASE_URL + "/" + id;

        this.mockMvc.perform(get(urlToExistingMachine)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getMachineByNonExistingIdShouldReturnHttpStatus404NotFound() throws Exception {
        long id = machineService.findAll().size() + 1;
        String urlToExistingMachine = BASE_URL + "/" + id;

        this.mockMvc.perform(get(urlToExistingMachine)).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void postMachineShouldReturnHttpStatus201Created() throws Exception {
        Machine machine = new Machine();
        machine.setSerialNumber("12345-67890");
        machine.setUdtNumber("322433544");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(machine);

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void putMachineByExistingIdShouldUpdateMachineAndReturnHttpStatus204NoContent() throws Exception {
        long id = machineService.findAll().size()-1;
        MockHttpServletRequestBuilder builder = createJsonRequest(id, "09876-54321", "322111222");

        this.mockMvc.perform(builder)
                .andExpect(status()
                        .isNoContent())
                .andDo(print());
    }

    @Test
    public void putMachineByNonExistingIdShouldReturnHttpStatus404NotFound() throws Exception {
        long id = machineService.findAll().size() + 1;
        MockHttpServletRequestBuilder builder = createJsonRequest(id, "111-222-3", "322098765");

        this.mockMvc.perform(builder)
                .andExpect(status()
                        .isNotFound())
                .andDo(print());
    }

    @Test
    public void deleteMachineByExistingIdShouldReturnHttpStatus204NoContent() throws Exception {
        Machine machine = new Machine();
        machineService.save(machine);
        long id = machineService.findAll().size();
        String urlToExistingMachine = BASE_URL + "/" + id;

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(urlToExistingMachine)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteMachineByNonExistingIdShouldReturnHttpStatus404NotFound() throws Exception {
        long id = machineService.findAll().size() + 1;
        String urlToNonExistingMachine = BASE_URL + "/" + id;

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(urlToNonExistingMachine)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private MockHttpServletRequestBuilder createJsonRequest(Long id, String serialNumber, String udtNumber) throws JsonProcessingException {
        Machine machine = new Machine();
        machine.setSerialNumber(serialNumber);
        machine.setUdtNumber(udtNumber);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(machine);

        return MockMvcRequestBuilders.put(BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(requestJson);
    }
}
