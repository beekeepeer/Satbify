package com.DAWIntegration.Satbify.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.DAWIntegration.Satbify.Chords.harmonise;

@RestController
public class SatbifyController {

    @PostMapping("/api")
    public String processString(@RequestBody String stringToProcess) {
        // Process the string as needed
        return harmonise(stringToProcess);

    }
}
