package se.iths.bird.controllers;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.iths.bird.dtos.BirdDto;
import se.iths.bird.dtos.BirdWeight;
import se.iths.bird.services.Service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(Controller.class)
class ControllerTest {
    @MockBean
    Service service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void callingWithUrlBirdsShouldReturnAllBirds() throws Exception {
        // Tell mockito what to return when calling methods on Service
        // For this we do not need to create a TestService class, Mockito will make an implementation for us.
        when(service.getAllBirds()).thenReturn(List.of(new BirdDto(1, "testname", "testtype", 0.1D, "female")));

        var result = mockMvc.perform(MockMvcRequestBuilders.get("/birds")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void callingWithUrlBirdIdShouldReturnBirdWithCorrespondingId() throws Exception {
        when(service.getOne(1)).thenReturn(Optional.of(new BirdDto(1, "testname", "testtype", 0.1D, "female")));

        var result = mockMvc.perform(MockMvcRequestBuilders.get("/birds/1")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void callingWithUrlBirdNonExistingIdShouldReturn404Error() throws Exception {
        when(service.getOne(1)).thenReturn(Optional.of(new BirdDto(1, "testname", "testtype", 0.1D, "female")));

        var result = mockMvc.perform(MockMvcRequestBuilders.get("/birds/2")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void callingWithUrlBirdSearchNameShouldReturnBirdWithCorrespondingName() throws Exception {
        when(service.searchByName("Jinx")).thenReturn(List.of(new BirdDto(1, "Jinx", "testtype", 0.1D, "female")));

        var result = mockMvc.perform(MockMvcRequestBuilders.get("/birds/search?name=Jinx")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void callingWithURLBirdsSearchGenderShouldReturnCorrespondingGender() throws Exception {
        when(service.searchByGender("male")).thenReturn(List.of(new BirdDto(1, "Jinx", "testtype", 0.1D, "male")));

        var result = mockMvc.perform(MockMvcRequestBuilders.get("/birds/search?name=Jinx")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void callingPOSTWithNewBirdShouldSaveNewBirdToServiceAndReturnNewBird() throws Exception {
        BirdDto newBird = new BirdDto(1,"Jinx", "testtype", 0.1D, "male");
        Gson gson = new Gson();
        when(service.createBird(newBird)).thenReturn(newBird);

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/birds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(newBird))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(201);
    }

    @Test
    void callingURLBirdsWithDeleteRequestShouldDeleteBird() {
        when(service.delete(1));
    }

    @Test
    void callingURLBirdsWithPutRequestShouldReplaceBird() throws Exception {
        BirdDto bird = new BirdDto(1,"Jinx", "testtype", 0.1D, "male");
        BirdDto replacementBird = new BirdDto(1,"Bogie", "testtype", 0.2D, "male");
        Gson gson = new Gson();

        when(service.replace(1, bird)).thenReturn(replacementBird);

        var result = mockMvc.perform(MockMvcRequestBuilders.put("/birds/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(replacementBird))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);

    }

    @Test
    void callingURLBirdsWithPatchRequestShouldUpdateBirdWeight() throws Exception {
        BirdDto bird = new BirdDto(1,"Jinx", "testtype", 0.1D, "male");
        BirdWeight birdWeight = new BirdWeight(0.5D);
        Gson gson = new Gson();

        when(service.update(1, birdWeight)).thenReturn(bird);

        var result = mockMvc.perform(MockMvcRequestBuilders.put("/birds/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(birdWeight))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }




}