package net.ckmk.api.controllers;

import net.ckmk.api.requests.GenerateUserRequest;
import net.ckmk.api.requests.LoginRequest;
import net.ckmk.api.requests.ValidateUserRequest;
import net.ckmk.api.responses.GenerateUserResponse;
import net.ckmk.api.responses.LoginResponse;
import net.ckmk.api.responses.Response;
import net.ckmk.api.responses.ValidateGenerationLinkResponse;
import net.ckmk.api.service.impl.MailServiceImpl;
import net.ckmk.api.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {
    @Autowired
    UserServiceImpl users;
    @Autowired
    MailServiceImpl mails;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return users.logIn(req);
    }

    @PostMapping("/generateUser")
    public GenerateUserResponse generateUser(@RequestBody GenerateUserRequest req){
        GenerateUserResponse r = users.generateUser(req);
        try{
            mails.sendMessage(req.getCreatedEmail(), "SecureVault Invitation (DedSec)", "You have been invited to join DedSec SecurityVault\n Link: " + "https://epickastrona.ddns.net/" + r.getUrlToken());
        } catch (Exception e){
            r.setSuccesfull(false);
            r.setUrlToken("Invalid Token due to email Address!");
            users.removeUser(req.getCreatedEmail());
        }
        return r;
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
