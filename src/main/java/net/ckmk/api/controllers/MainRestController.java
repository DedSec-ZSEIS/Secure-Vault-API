package net.ckmk.api.controllers;

import net.ckmk.api.other.Env;
import net.ckmk.api.prototypes.User;
import net.ckmk.api.requests.*;
import net.ckmk.api.responses.*;
import net.ckmk.api.service.impl.FileServiceImpl;
import net.ckmk.api.service.impl.MailServiceImpl;
import net.ckmk.api.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class MainRestController {
    private final HashMap<String, String> passChanging = new HashMap<>();
    @Autowired
    UserServiceImpl users;
    @Autowired
    MailServiceImpl mails;
    @Autowired
    FileServiceImpl files;
    @Autowired
    Env env;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return users.logIn(req);
    }

    @PostMapping("/generateUser")
    public GenerateUserResponse generateUser(@RequestBody GenerateUserRequest req){
        GenerateUserResponse r = users.generateUser(req);
        try {
            mails.sendMessage(req.getCreatedEmail(), "SecureVault Invitation (DedSec)", "You have been invited to join DedSec SecurityVault\nLink: " + env.getFrontEndUrl() + "/activate/" + r.getUrlToken());
        } catch (Exception e){
            r.setSuccessful(false);
            r.setUrlToken("Invalid Token due to email Address!");
            users.removeUser(req.getCreatedEmail());
            return r;
        }
        return r;
    }

    @PostMapping("/validateLink/{uat}")
    public ResponseEntity<ValidateGenerationLinkResponse> validateGenerationLink(@PathVariable String uat){
        ValidateGenerationLinkResponse r = users.validateGenerationLink(uat);
        if (r.getSuccessful()){
            return ResponseEntity.ok(r);
        } else return ResponseEntity.notFound().build();
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

    @PostMapping("/getFiles")
    public ResponseEntity<GetFilesResponse> getFiles(@RequestBody GetFilesRequest req){
        if (!users.checkLoggedIn(req.getEmail(), req.getUat())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        GetFilesResponse response;
        if (req.getFileIds() == null || req.getFileIds().isEmpty()){
            response = new GetFilesResponse(files.getFiles(req.getEmail(), req.getUat()));
            response.setSuccessful(response.getFiles() != null);
        } else {
            response = new GetFilesResponse(files.getFiles(req.getEmail(), req.getUat(), req.getFileIds()));
            response.setSuccessful(response.getFiles() != null);
        }
        if (response.getFiles().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/removeUsers")
    public Response removeUsers(@RequestBody RemoveUsersRequest req){
        Response response = new Response();
        response.setSuccessful(users.removeUsers(req.getEmail(), req.getUat(), req.getUserIds()));
        return response;
    }

    @PostMapping("/uploadFile")
    public Response upload(@RequestParam("email") String email, @RequestParam("uat") String uat, @RequestParam("file") MultipartFile file){
        Response r = new Response();
        r.setSuccessful(files.saveFile(email, uat, file));
        return r;
    }

    @PostMapping(value = "/getFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody byte[] getFile(@RequestBody GetFileRequest req) throws IOException {
        return files.getFile(req);
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Response> removeFile(@RequestBody RemoveFileRequest req){
        if (!users.checkLoggedIn(req.getEmail(), req.getUat())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Response r = new Response().setSuccessful(files.removeFile(req));
        if (r.getSuccessful()){
            return ResponseEntity.ok(r);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/generatePassResetLink")
    public ResponseEntity<Response> genResetPass(@RequestBody ResetPassRequest req) {
        if (passChanging.containsKey(req.getEmail())){
            return ResponseEntity.ok(new Response().setSuccessful(false));
        }
        String passToken = UUID.randomUUID().toString() + UUID.randomUUID() + UUID.randomUUID();
        passChanging.put(req.getEmail(), passToken);
        mails.sendMessage(req.getEmail(), "Password Reset", "Here is the link to reset your password\nLink: " + env.getFrontEndUrl() + "/resetPass/" + passToken);
        return ResponseEntity.ok(new Response());
    }

    @PostMapping("/validatePassLink/{token}")
    public ResponseEntity<Response> validateLink(@PathVariable String token){
        if (passChanging.containsValue(token)){
            return ResponseEntity.ok(new Response().setSuccessful(true));
        } else return ResponseEntity.notFound().build();
    }

    @PostMapping("/resetPass/{passResetToken}")
    public ResponseEntity<Response> resetPass(@PathVariable String passResetToken, @RequestBody PassResetRequest req){
        String token = "";
        String email = "";
        for (String em : passChanging.keySet()){
            if (passChanging.get(em).equals(passResetToken)){
                token = passChanging.get(em);
                email = em;
                break;
            }
        }
        if (token.equals("") || email.equals("")){
            return ResponseEntity.ok(new Response().setSuccessful(false));
        }
        passChanging.remove(email);
        return ResponseEntity.ok(new Response().setSuccessful(users.resetPass(email, req.getNewPass())));
    }
}
