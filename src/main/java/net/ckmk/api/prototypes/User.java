package net.ckmk.api.prototypes;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class User {
    private String email;
    private String uat;
    private String fullName;
    private boolean isAdmin;
    private String isAllowed;
    private int dbSpaceTaken;

    public User(String email, String uat, boolean isAdmin, String isAllowed, String fullName, int dbSpaceTaken){
        this.email = email;
        this.uat = uat;
        this.isAdmin = isAdmin;
        this.isAllowed = isAllowed;
        this.fullName = fullName;
        this.dbSpaceTaken = dbSpaceTaken;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String isAllowed() {
        return isAllowed;
    }

    public void setAllowed(String allowed) {
        isAllowed = allowed;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getDbSpaceTaken() {
        return dbSpaceTaken;
    }

    public void setDbSpaceTaken(int dbSpaceTaken) {
        this.dbSpaceTaken = dbSpaceTaken;
    }
}
