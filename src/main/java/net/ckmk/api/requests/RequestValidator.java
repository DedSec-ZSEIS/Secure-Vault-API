package net.ckmk.api.requests;

import net.ckmk.api.database.DbManager;

public class RequestValidator {
    private String email;
    private String uat;

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
