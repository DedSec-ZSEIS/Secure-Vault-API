package net.ckmk.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    @JsonProperty
    private boolean successful;

    public Response(){
        successful =true;
    }

    public boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
