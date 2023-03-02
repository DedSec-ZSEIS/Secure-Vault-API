package net.ckmk.api.service;

import net.ckmk.api.prototypes.User;
import net.ckmk.api.requests.GenerateUserRequest;
import net.ckmk.api.requests.LoginRequest;
import net.ckmk.api.requests.ValidateUserRequest;
import net.ckmk.api.responses.GenerateUserResponse;
import net.ckmk.api.responses.LoginResponse;
import net.ckmk.api.responses.Response;
import net.ckmk.api.responses.ValidateGenerationLinkResponse;

import java.util.ArrayList;

public interface UserService {
    GenerateUserResponse generateUser(GenerateUserRequest req);
    Response validateNewUser(ValidateUserRequest req);
    LoginResponse logIn(LoginRequest req);
    boolean validateCreationRequest(String email, String uat);
    boolean validateAdminRequest(String email, String uat);
    User getUserByEmail(String email, String uat);
    User findUser(String finding, String finderEmail, String findersUat);
    void removeUser(String email);
    ArrayList<User> getUsers(String email, String uat);
    ArrayList<User> getUsers(String email, String uat, ArrayList<Integer> ids);
    ValidateGenerationLinkResponse validateGenerationLink(String uat);
    boolean removeUsers(String email, String uat, ArrayList<Integer> ids);
    boolean resetPass(String email, String newPass);
    boolean checkLoggedIn(String email, String uat);
}
