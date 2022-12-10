package net.ckmk.api.controllers;

import net.ckmk.api.requests.GenerateUserRequest;
import net.ckmk.api.requests.LoginRequest;
import net.ckmk.api.requests.ValidateUserRequest;
import net.ckmk.api.responses.GenerateUserResponse;
import net.ckmk.api.responses.LoginResponse;
import net.ckmk.api.responses.Response;
import net.ckmk.api.responses.ValidateGenerationLinkResponse;
import net.ckmk.api.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {

    UserServiceImpl users = new UserServiceImpl();

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return users.logIn(req);
    }

    @PostMapping("/generateUser")
    public GenerateUserResponse generateUser(@RequestBody GenerateUserRequest req){
        return users.generateUser(req);
    }

    @PostMapping("/validateLink/{uat}")
    public ValidateGenerationLinkResponse validateGenerationLink(@PathVariable String uat){
        return new ValidateGenerationLinkResponse(uat);
    }

    @PostMapping("/validateUser")
    public Response validateUser(@RequestBody ValidateUserRequest req){
        return users.validateNewUser(req);
    }
}
