package net.ckmk.api.prototypes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SafeUser {
    @JsonProperty
    private int id;
    @JsonProperty
    private String email;
    @JsonProperty
    private String fullName;
    @JsonProperty
    private boolean admin;
    @JsonProperty
    private String status;
    @JsonProperty
    private int dbSpaceTaken;

    public SafeUser(int id, String email, boolean admin, String status, String fullName, int dbSpaceTaken){
        this.id = id;
        this.email = email;
        this.admin = admin;
        this.status = status;
        this.fullName = fullName;
        this.dbSpaceTaken = dbSpaceTaken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String isAllowed() {
        return status;
    }

    public void setAllowed(String allowed) {
        status = allowed;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
