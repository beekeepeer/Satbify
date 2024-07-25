package com.DAWIntegration.Satbify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String displayMainPage() {
        return "main_page";
    }

    // explain how it words and requirements.
    @GetMapping("/download")
    public String displayDownloadPage() {
        return "download_page";
    }

    @GetMapping("/donate")
    public String displayDonatePage() {
        return "donate_page";
    }

    @GetMapping("/versions")
    public String displayVersionsPage() {
        return "versions_page";
    }
}
