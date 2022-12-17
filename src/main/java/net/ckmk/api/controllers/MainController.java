package net.ckmk.api.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class MainController implements ErrorController {

    @RequestMapping("/error")
    public String error(){
        return "index.html";
    }

    @PostMapping("/error")
    public String errorPost(){
        return "An Error has occurred while trying to connect to the api!";
    }

    @GetMapping("/")
    public String home(){
        return "index.html";
    }
}
