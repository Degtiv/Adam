package space.deg.adam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {

    @GetMapping("/landing")
    public String greeting(Model model) {
        return "landing";
    }

    @GetMapping("/")
    public String landing() {
        return "redirect:/landing";
    }

    @GetMapping("/firstEnter")
    public String firstEnter5() {
        return "firstEnter";
    }

}