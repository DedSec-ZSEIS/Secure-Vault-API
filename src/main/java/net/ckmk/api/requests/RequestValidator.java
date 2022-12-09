package net.ckmk.api.requests;

import net.ckmk.api.database.DbManager;

public class RequestValidator {
    private String email;
    private String uat;
    private final DbManager db = new DbManager();

    public boolean validateRequest(){
        if (!db.isDbEnabled()){
            return false;
        }
        return db.validateToken(email, uat);
    }

    public boolean validateCreationRequest(){
        if (!db.isDbEnabled()){
            return false;
        }
        return db.validateCreationToken(email, uat);
    }

    public boolean validateAdminRequest(){
        if (!db.isDbEnabled()){
            return false;
        }
        return db.validateTokenAdmin(email, uat);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUat() {
        return uat;
    }

    public void setUat(String uat) {
        this.uat = uat;
    }
}
