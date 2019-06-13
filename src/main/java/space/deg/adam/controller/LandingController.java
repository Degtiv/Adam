package space.deg.adam.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class LandingController {

    @GetMapping("/landing")
    public String greeting(Map<String, Object> model) {
        return "landing";
    }

}