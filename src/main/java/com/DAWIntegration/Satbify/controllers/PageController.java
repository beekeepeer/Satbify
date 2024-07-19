package com.DAWIntegration.Satbify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String displayMainPage() {
        return "main_page";
    }
}
