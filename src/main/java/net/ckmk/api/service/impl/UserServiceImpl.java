package net.ckmk.api.service.impl;

import net.ckmk.api.database.DbManager;
import net.ckmk.api.prototypes.User;
import net.ckmk.api.requests.GenerateUserRequest;
import net.ckmk.api.requests.LoginRequest;
import net.ckmk.api.requests.ValidateUserRequest;
import net.ckmk.api.responses.GenerateUserResponse;
import net.ckmk.api.responses.LoginResponse;
import net.ckmk.api.responses.Response;
import net.ckmk.api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final DbManager db;

    public UserServiceImpl(){
        this.db = new DbManager();
    }

    @Override
    public GenerateUserResponse generateUser(GenerateUserRequest req) {
        if (db.isDbEnabled() && validateAdminRequest(req.getEmail(), req.getUat())){
            return db.generateUser(req.getCreatedEmail(), req.isCreatedIsAdmin());
        }
        return new GenerateUserResponse(null, false, false);
    }

    @Override
    public Response validateNewUser(ValidateUserRequest req) {
        Response r = new Response();
        r.setSuccesfull(false);
        if (db.isDbEnabled()){
            if (!validateCreationRequest(req.getEmail(), req.getUat())){
                return r;
            }
            r.setSuccesfull(db.validateUser(req.getEmail(), req.getNewPass(), req.getNewFullName()));
        }
        return r;
    }

    @Override
    public LoginResponse logIn(LoginRequest req) {
        if (db.isDbEnabled()){
            return db.login(req.getEmail(), req.getPassword());
        }
        return new LoginResponse(null, false, null, false, null, false);
    }

    @Override
    public boolean validateCreationRequest(String email, String uat){
        if (!db.isDbEnabled()){
            return false;
        }
        return db.validateCreationToken(email, uat);
    }
    @Override
    public boolean validateAdminRequest(String email, String uat){
        if (!db.isDbEnabled()){
            return false;
        }
        return db.validateTokenAdmin(email, uat);
    }
    @Override
    public User getUserByEmail(String email, String uat) {
        if (db.isDbEnabled() && db.validateToken(email, uat)){
            return db.getUser(email);
        }
        return null;
    }
    @Override
    public User findUser(String finding, String finderEmail, String findersUat){
        if (db.isDbEnabled() && db.validateTokenAdmin(finderEmail, findersUat)){
            return db.getUser(finding);
        }
        return null;
    }
}
