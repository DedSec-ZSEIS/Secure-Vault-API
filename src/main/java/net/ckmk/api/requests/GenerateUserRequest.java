package net.ckmk.api.requests;

import net.ckmk.api.database.DbManager;
import net.ckmk.api.responses.GenerateUserResponse;

public class GenerateUserRequest extends RequestValidator{
    private String createdEmail;
    private boolean createdIsAdmin;
    private final DbManager db = new DbManager();

    public String getCreatedEmail() {
        return createdEmail;
    }

    public void setCreatedEmail(String createdEmail) {
        this.createdEmail = createdEmail;
    }

    public boolean isCreatedIsAdmin() {
        return createdIsAdmin;
    }

    public void setCreatedIsAdmin(boolean createdIsAdmin) {
        this.createdIsAdmin = createdIsAdmin;
    }
    public GenerateUserResponse generate(){
        if (db.isDbEnabled() && validateAdminRequest()){
            return db.generateUser(createdEmail, createdIsAdmin);
        }
        return new GenerateUserResponse(null, false, false);
    }
}
