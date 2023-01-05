package net.ckmk.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse extends Response{
    @JsonProperty
    private String email;
    @JsonProperty
    private boolean hasAccount;
    @JsonProperty
    private String uat;
    @JsonProperty
    private boolean admin;
    @JsonProperty
    private String status;

    public LoginResponse(String email, boolean hasAccount, String uat, boolean admin, String status, boolean succesfull){
        this.email = email;
        this.hasAccount = hasAccount;
        this.uat = uat;
        this.admin = admin;
        this.status = status;
        this.setSuccessful(succesfull);
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
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isHasAccount() {
        return hasAccount;
    }

    public void setHasAccount(boolean hasAccount) {
        this.hasAccount = hasAccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
