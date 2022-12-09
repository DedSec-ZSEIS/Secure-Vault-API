package net.ckmk.api.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class LoginResponse extends Response{
    private String email;
    private boolean hasAccount;
    private String uat;
    private boolean isAdmin;
    private String isAllowed;

    public LoginResponse(String email, boolean hasAccount, String uat, boolean isAdmin, String isAllowed, boolean succesfull){
        this.email = email;
        this.hasAccount = hasAccount;
        this.uat = uat;
        this.isAdmin = isAdmin;
        this.isAllowed = isAllowed;
        this.setSuccesfull(succesfull);
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

    public boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getAllowed() {
        return isAllowed;
    }

    public void setAllowed(String allowed) {
        isAllowed = allowed;
    }

    public boolean isHasAccount() {
        return hasAccount;
    }

    public void setHasAccount(boolean hasAccount) {
        this.hasAccount = hasAccount;
    }
}
