package com.DAWIntegration.Satbify.controllers;

import com.DAWIntegration.Satbify.module.RequestDeserialized;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.DAWIntegration.Satbify.Chords.harmonise;

@RestController
public class SatbifyController {

    @PostMapping("/api")
    public String processString(@RequestBody RequestDeserialized request) {
        return harmonise(request.notes());
    }
}
