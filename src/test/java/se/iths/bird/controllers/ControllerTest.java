package se.iths.bird.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.iths.bird.dtos.BirdDto;
import se.iths.bird.services.Service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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


}