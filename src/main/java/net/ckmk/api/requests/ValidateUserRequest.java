package net.ckmk.api.requests;

import net.ckmk.api.database.DbManager;
import net.ckmk.api.responses.Response;

public class ValidateUserRequest extends RequestValidator{
    private String newPass;
    private String newFullName;

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public void setNewFullName(String newFullName) {
        this.newFullName = newFullName;
    }

    public String getNewPass() {
        return newPass;
    }

    public String getNewFullName() {
        return newFullName;
    }
}
