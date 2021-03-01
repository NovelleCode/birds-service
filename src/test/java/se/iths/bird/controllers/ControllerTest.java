package se.iths.bird.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.bird.services.Service;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(Controller.class)
class ControllerTest {
    @MockBean
    Service service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void callingWithUrlBirdsShouldReturnAllBirds() {

    }



}