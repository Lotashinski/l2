package by.grsu.les2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/")
    public String mainPage(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "home";
    }
}
