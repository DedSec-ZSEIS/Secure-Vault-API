package net.ckmk.api.controllers;

import net.ckmk.api.prototypes.SafeUser;
import net.ckmk.api.prototypes.User;
import net.ckmk.api.requests.*;
import net.ckmk.api.responses.*;
import net.ckmk.api.service.impl.FileServiceImpl;
import net.ckmk.api.service.impl.MailServiceImpl;
import net.ckmk.api.service.impl.UserServiceImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;

@RestController
public class MainRestController {
    @Autowired
    UserServiceImpl users;
    @Autowired
    MailServiceImpl mails;
    @Autowired
    FileServiceImpl files;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return users.logIn(req);
    }

    @PostMapping("/generateUser")
    public GenerateUserResponse generateUser(@RequestBody GenerateUserRequest req){
        GenerateUserResponse r = users.generateUser(req);
        try {
            mails.sendMessage(req.getCreatedEmail(), "SecureVault Invitation (DedSec)", "You have been invited to join DedSec SecurityVault\nLink: " + "https://dedsec-secure-vault.vercel.app/activate/" + r.getUrlToken());
        } catch (Exception e){
            r.setSuccessful(false);
            r.setUrlToken("Invalid Token due to email Address!");
            users.removeUser(req.getCreatedEmail());
            return r;
        }
        return r;
    }

    @PostMapping("/validateLink/{uat}")
    public ValidateGenerationLinkResponse validateGenerationLink(@PathVariable String uat){
        return users.validateGenerationLink(uat);
    }

    @PostMapping("/validateUser")
    public Response validateUser(@RequestBody ValidateUserRequest req){
        return users.validateNewUser(req);
    }

    @PostMapping("/help")
    public Response help(@RequestParam("email") String email, @RequestParam("problem") String problem){
        Response r = new Response();
        if (!mails.emailExists(email)){
            r.setSuccessful(false);
            return r;
        }
        LoginRequest loginRequest = new LoginRequest("root@root.net", "ddaf35a193617abacc417349ae20413112e6fa4e89a97ea20a9eeee64b55d39a2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f");
        LoginResponse lr = users.logIn(loginRequest);
        User u = users.findUser(email, lr.getEmail(), lr.getUat());
        boolean hasAccount = true;
        if (u==null){
            u = new User(-1, email, null, false, null, null, 0);
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

    @PostMapping("/getUsers")
    public GetUsersResponse getUsers(@RequestBody GetUsersRequest req){
        GetUsersResponse response;
        if (req.getUserIds() == null || req.getUserIds().isEmpty()){
            response = new GetUsersResponse(users.getUsers(req.getEmail(), req.getUat()));
            response.setSuccessful(response.getUsers() != null);
            return response;
        }
        response = new GetUsersResponse(users.getUsers(req.getEmail(), req.getUat(), req.getUserIds()));
        response.setSuccessful(response.getUsers() != null);
        return response;
    }

    @PostMapping("/removeUsers")
    public Response removeUsers(@RequestBody RemoveUsersRequest req){
        Response response = new Response();
        response.setSuccessful(users.removeUsers(req.getEmail(), req.getUat(), req.getUserIds()));
        return response;
    }

    @PostMapping("/uploadFile")
    public Response upload(@RequestParam("email") String email, @RequestParam("uat") String uat, @RequestParam("file") MultipartFile file){
        files.saveFile(email, uat, file);
        return new Response();
    }

    @PostMapping(value = "/getFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody byte[] getFile(@RequestBody GetFileRequest req) throws IOException {
        return files.getFile(req);
    }
}
