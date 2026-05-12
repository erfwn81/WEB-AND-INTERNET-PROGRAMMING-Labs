package cs3220.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({ "/", "/index" })
    public String index() {
        return "index"; // login + registration page
    }

    @GetMapping("/messageboard")
    public String messageBoard() {
        return "messageboard";
    }
}