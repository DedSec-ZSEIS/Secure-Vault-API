package net.ckmk.api.requests;

import net.ckmk.api.database.DbManager;
import net.ckmk.api.prototypes.User;
import net.ckmk.api.responses.LoginResponse;

public class LoginRequest{

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
