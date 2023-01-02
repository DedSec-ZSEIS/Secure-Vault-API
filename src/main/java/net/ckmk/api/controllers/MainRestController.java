package net.ckmk.api.controllers;

import net.ckmk.api.prototypes.User;
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
import org.springframework.web.bind.annotation.*;

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
        try {
            mails.sendMessage(req.getCreatedEmail(), "SecureVault Invitation (DedSec)", "You have been invited to join DedSec SecurityVault\nLink: " + "https://dedsec-secure-vault.vercel.app/activate" + r.getUrlToken());
        } catch (Exception e){
            r.setSuccesfull(false);
            r.setUrlToken("Invalid Token due to email Address!");
            users.removeUser(req.getCreatedEmail());
            return r;
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

    @PostMapping("/help")
    public Response help(@RequestParam("email") String email, @RequestParam("problem") String problem){
        Response r = new Response();
        if (!mails.emailExists(email)){
            r.setSuccesfull(false);
            return r;
        }
        LoginRequest loginRequest = new LoginRequest("root@root.net", "ddaf35a193617abacc417349ae20413112e6fa4e89a97ea20a9eeee64b55d39a2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f");
        LoginResponse lr = users.logIn(loginRequest);
        User u = users.findUser(email, lr.getEmail(), lr.getUat());
        boolean hasAccount = true;
        if (u==null){
            u = new User(email, null, false, null, null, 0);
            hasAccount = false;
        }
        mails.sendMessage("dedsecservice@gmail.com", "HelpService - Help Request from " + email, "The following help request is from an email " + email +
                ".\nExistence in the database: " + hasAccount +
                ".\nFull Name: " + u.getFullName() +
                ".\nIs admin: " + u.isAdmin() +
                ".\nDatabase space Taken: " + u.getDbSpaceTaken() +
                ".\nWritten problem: " + problem);

        mails.sendMessage(email,"Help request confirmation","Thank you for reporting the problem, we will write back as soon as possible.\n" +
                "Organization DedSec\n" +
                "@Copyright 2022");
        return r;
    }
}
