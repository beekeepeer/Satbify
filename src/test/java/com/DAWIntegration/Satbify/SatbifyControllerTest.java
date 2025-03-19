package com.DAWIntegration.Satbify;
import com.DAWIntegration.Satbify.controllers.SatbifyController;
import com.DAWIntegration.Satbify.module.RequestDeserialized;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;

@WebMvcTest(SatbifyController.class)
public class SatbifyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private JacksonTester<RequestDeserialized> json;

    @Autowired
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
    }



    @Test
    public void testProcessString() throws Exception {
        String path = "src\\main\\resources\\data\\incomingDataExampleSmall.json";
        String requestJson = new String(Files.readAllBytes(Paths.get(path)));

        mockMvc.perform(post("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }
}