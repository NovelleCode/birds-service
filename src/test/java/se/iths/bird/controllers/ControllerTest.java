package se.iths.bird.controllers;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;
import se.iths.bird.dtos.BirdDto;
import se.iths.bird.dtos.BirdWeight;
import se.iths.bird.services.Service;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
class ControllerTest {
    @MockBean
    Service service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void callingWithUrlBirdsShouldReturnAllBirds() throws Exception {
        when(service.getAllBirds()).thenReturn(List.of(new BirdDto(1, "testname", "testtype", 0.1D, "female")));

        mockMvc.perform(MockMvcRequestBuilders.get("/birds")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void callingWithUrlBirdIdShouldReturnBirdWithCorrespondingId() throws Exception {
        when(service.getOne(1)).thenReturn(Optional.of(new BirdDto(1, "testname", "testtype", 0.1D, "female")));

        mockMvc.perform(MockMvcRequestBuilders.get("/birds/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void callingWithUrlBirdNonExistingIdShouldReturn404Error() throws Exception {
        when(service.getOne(1)).thenReturn(Optional.of(new BirdDto(1, "testname", "testtype", 0.1D, "female")));

        mockMvc.perform(MockMvcRequestBuilders.get("/birds/{id}", 2)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void callingWithUrlBirdSearchNameShouldReturnBirdWithCorrespondingName() throws Exception {
        when(service.searchByName("Jinx")).thenReturn(List.of(new BirdDto(1, "Jinx", "testtype", 0.1D, "female")));

        mockMvc.perform(MockMvcRequestBuilders.get("/birds/search?name=Jinx")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void callingWithURLBirdsSearchGenderShouldReturnCorrespondingGender() throws Exception {
        when(service.searchByGender("male")).thenReturn(List.of(new BirdDto(1, "Jinx", "testtype", 0.1D, "male")));

        mockMvc.perform(MockMvcRequestBuilders.get("/birds/search?name=Jinx")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void callingPOSTWithNewBirdShouldSaveNewBirdToServiceAndReturnNewBird() throws Exception {
        BirdDto newBird = new BirdDto(1, "Jinx", "testtype", 0.1D, "male");
        Gson gson = new Gson();
        when(service.createBird(newBird)).thenReturn(newBird);

        mockMvc.perform(MockMvcRequestBuilders.post("/birds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(newBird))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void callingURLBirdsWithDeleteRequestShouldReturnStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/birds/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void callingURLBirdsWithDeleteRequestShouldReturnStatusNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(service).delete(1);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/birds/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void callingURLBirdsWithPutRequestShouldReplaceBird() throws Exception {
        BirdDto bird = new BirdDto(1, "Jinx", "testtype", 0.1D, "male");
        BirdDto replacementBird = new BirdDto(1, "Bogie", "testtype", 0.2D, "male");
        Gson gson = new Gson();

        when(service.replace(1, bird)).thenReturn(replacementBird);

        mockMvc.perform(MockMvcRequestBuilders.put("/birds/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(replacementBird))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void callingURLBirdsWithPatchRequestShouldUpdateBirdWeight() throws Exception {
        BirdDto bird = new BirdDto(1, "Jinx", "testtype", 0.1D, "male");
        BirdWeight birdWeight = new BirdWeight(10.5D);
        Gson gson = new Gson();

        when(service.update(1, birdWeight)).thenReturn(bird);

        mockMvc.perform(MockMvcRequestBuilders.put("/birds/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(birdWeight))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}