package com.harshpal.scorer.bowling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/scorer/home")
    public String home() {
        return "index";
    }
}
