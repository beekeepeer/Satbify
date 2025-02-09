package com.DAWIntegration.Satbify.controllers;

import com.DAWIntegration.Satbify.Refactor.SatbifyFacade;
import com.DAWIntegration.Satbify.module.RequestDeserialized;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SatbifyController {

    @PostMapping("/api")
    public String processString(@RequestBody RequestDeserialized request) {
        return new SatbifyFacade().processRequest(request.notes());
    }
}