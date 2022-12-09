package net.ckmk.api.requests;

import net.ckmk.api.database.DbManager;
import net.ckmk.api.prototypes.User;
import net.ckmk.api.responses.LoginResponse;

public class LoginRequest{

    private String email;
    private String password;
    private final DbManager db = new DbManager();

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LoginResponse logIn(){
        if (db.isDbEnabled()){
            return db.login(email, password);
        }
        return new LoginResponse(null, false, null, false, null, false);
    }
}
