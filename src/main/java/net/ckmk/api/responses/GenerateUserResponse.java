package net.ckmk.api.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class GenerateUserResponse extends Response{
    private String urlToken;
    private boolean isInDb;

    public GenerateUserResponse(String urlToken, boolean isInDb, boolean succesfull) {
        this.urlToken = urlToken;
        this.isInDb = isInDb;
        setSuccesfull(succesfull);
    }

    public String getUrlToken() {
        return urlToken;
    }

    public void setUrlToken(String urlToken) {
        this.urlToken = urlToken;
    }

    public boolean getIsInDb() {
        return isInDb;
    }

    public void setIsInDb(boolean isInDb) {
        this.isInDb = isInDb;
    }
}
