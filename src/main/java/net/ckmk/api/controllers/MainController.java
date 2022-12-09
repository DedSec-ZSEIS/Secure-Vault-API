package net.ckmk.api.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController implements ErrorController {

    @RequestMapping("/error")
    public String error(){
        return "index.html";
    }

    @GetMapping("/")
    public String home(){
        return "index.html";
    }
}
