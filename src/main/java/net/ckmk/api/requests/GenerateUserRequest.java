package net.ckmk.api.requests;

import net.ckmk.api.database.DbManager;
import net.ckmk.api.responses.GenerateUserResponse;

public class GenerateUserRequest extends RequestValidator{
    private String createdEmail;


    public String getCreatedEmail() {
        return createdEmail;
    }

    public void setCreatedEmail(String createdEmail) {
        this.createdEmail = createdEmail;
    }
}
