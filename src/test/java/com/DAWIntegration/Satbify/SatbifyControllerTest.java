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
        // only Root key keySwitches
        // String requestJson = "{\"customerId\":\"12345\",\"token\":\"a7f5c3b7d98ef43e6a5a7c8b9d3e1f6d\",\"notes\":[{\"track\":\"0\",\"note\":\"5\",\"velocity\":\"96\",\"start\":\"235.289071016113180\",\"end\":\"237.318482780819350\"},{\"track\":\"0\",\"note\":\"12\",\"velocity\":\"96\",\"start\":\"235.289071016113180\",\"end\":\"235.965541604348570\"},{\"track\":\"0\",\"note\":\"19\",\"velocity\":\"96\",\"start\":\"235.965541604348570\",\"end\":\"236.642012192583960\"},{\"track\":\"0\",\"note\":\"21\",\"velocity\":\"96\",\"start\":\"236.642012192583960\",\"end\":\"237.318482780819350\"},{\"track\":\"0\",\"note\":\"17\",\"velocity\":\"96\",\"start\":\"237.318482780819350\",\"end\":\"237.994953369054740\"},{\"track\":\"0\",\"note\":\"2\",\"velocity\":\"96\",\"start\":\"237.318482780819350\",\"end\":\"240.024365133760940\"},{\"track\":\"0\",\"note\":\"14\",\"velocity\":\"96\",\"start\":\"237.994953369054740\",\"end\":\"238.671423957290130\"},{\"track\":\"0\",\"note\":\"19\",\"velocity\":\"96\",\"start\":\"238.671423957290130\",\"end\":\"239.347894545525550\"},{\"track\":\"0\",\"note\":\"12\",\"velocity\":\"96\",\"start\":\"239.347894545525550\",\"end\":\"240.024365133760940\"},{\"track\":\"0\",\"note\":\"0\",\"velocity\":\"96\",\"start\":\"240.024365133760940\",\"end\":\"240.700835721996330\"},{\"track\":\"0\",\"note\":\"14\",\"velocity\":\"96\",\"start\":\"240.024365133760940\",\"end\":\"240.700835721996330\"},{\"track\":\"0\",\"note\":\"14\",\"velocity\":\"96\",\"start\":\"240.700835721996330\",\"end\":\"241.377306310231720\"},{\"track\":\"0\",\"note\":\"19\",\"velocity\":\"96\",\"start\":\"241.377306310231720\",\"end\":\"242.053776898467110\"},{\"track\":\"0\",\"note\":\"12\",\"velocity\":\"96\",\"start\":\"242.053776898467110\",\"end\":\"242.730247486702520\"}]}";
        // only NON Root key keySwitches
        String requestJson = "{\"customerId\":\"12345\",\"token\":\"a7f5c3b7d98ef43e6a5a7c8b9d3e1f6d\",\"notes\":[{\"track\":\"0\",\"note\":\"12\",\"velocity\":\"96\",\"start\":\"235.289071016113180\",\"end\":\"235.965541604348570\"},{\"track\":\"0\",\"note\":\"19\",\"velocity\":\"96\",\"start\":\"235.965541604348570\",\"end\":\"236.642012192583960\"},{\"track\":\"0\",\"note\":\"21\",\"velocity\":\"96\",\"start\":\"236.642012192583960\",\"end\":\"237.318482780819350\"},{\"track\":\"0\",\"note\":\"29\",\"velocity\":\"96\",\"start\":\"236.642012192583960\",\"end\":\"237.318482780819350\"},{\"track\":\"0\",\"note\":\"17\",\"velocity\":\"96\",\"start\":\"237.318482780819350\",\"end\":\"237.994953369054740\"},{\"track\":\"0\",\"note\":\"34\",\"velocity\":\"96\",\"start\":\"237.318482780819350\",\"end\":\"237.994953369054740\"},{\"track\":\"0\",\"note\":\"14\",\"velocity\":\"96\",\"start\":\"237.994953369054740\",\"end\":\"238.671423957290130\"},{\"track\":\"0\",\"note\":\"19\",\"velocity\":\"96\",\"start\":\"238.671423957290130\",\"end\":\"239.347894545525550\"},{\"track\":\"0\",\"note\":\"38\",\"velocity\":\"96\",\"start\":\"238.671423957290130\",\"end\":\"239.347894545525550\"},{\"track\":\"0\",\"note\":\"12\",\"velocity\":\"96\",\"start\":\"239.347894545525550\",\"end\":\"240.024365133760940\"},{\"track\":\"0\",\"note\":\"14\",\"velocity\":\"96\",\"start\":\"240.024365133760940\",\"end\":\"240.700835721996330\"},{\"track\":\"0\",\"note\":\"40\",\"velocity\":\"96\",\"start\":\"240.024365133760940\",\"end\":\"240.700835721996330\"},{\"track\":\"0\",\"note\":\"14\",\"velocity\":\"96\",\"start\":\"240.700835721996330\",\"end\":\"241.377306310231720\"},{\"track\":\"0\",\"note\":\"19\",\"velocity\":\"96\",\"start\":\"241.377306310231720\",\"end\":\"242.053776898467110\"},{\"track\":\"0\",\"note\":\"12\",\"velocity\":\"96\",\"start\":\"242.053776898467110\",\"end\":\"242.730247486702520\"}]}";

        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                // .andExpect(content().string("Expected Result from SatbifyFacade")) // nothing special, only debug.
                ; // Replace with the actual expected result
    }
}