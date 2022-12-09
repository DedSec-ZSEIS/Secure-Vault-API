package net.ckmk.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    @JsonProperty
    private boolean succesfull;

    public boolean getSuccesfull() {
        return succesfull;
    }

    public void setSuccesfull(boolean succesfull) {
        this.succesfull = succesfull;
    }
}
