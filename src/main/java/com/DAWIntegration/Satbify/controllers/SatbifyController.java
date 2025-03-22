package com.DAWIntegration.Satbify.controllers;

import com.DAWIntegration.Satbify.module.RequestDeserialized;
import com.DAWIntegration.Satbify.service.SatbifyFacade;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SatbifyController {

    @PostMapping("/api")
    public String processString(@RequestBody RequestDeserialized request) {
        return SatbifyFacade.getInstance(request.notes()).processRequest();
    }
}