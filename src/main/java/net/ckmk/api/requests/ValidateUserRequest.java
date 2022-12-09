package net.ckmk.api.requests;

import net.ckmk.api.database.DbManager;
import net.ckmk.api.responses.Response;

public class ValidateUserRequest extends RequestValidator{

    private String newPass;
    private String newFullName;
    private final DbManager db = new DbManager();

    public Response validate(){
        Response r = new Response();
        r.setSuccesfull(false);
        if (!validateCreationRequest()){
            return r;
        }
        r.setSuccesfull(db.validateUser(getEmail(), newPass, newFullName));
        return r;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public void setNewFullName(String newFullName) {
        this.newFullName = newFullName;
    }
}
