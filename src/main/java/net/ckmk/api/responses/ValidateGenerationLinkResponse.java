package net.ckmk.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidateGenerationLinkResponse extends Response{
    @JsonProperty
    private final boolean exists;
    @JsonProperty
    private final String uat;
    @JsonProperty
    private String email;

    public ValidateGenerationLinkResponse(String email, String uat, boolean exists) {
        this.uat = uat;
        this.exists = exists;
        this.email = email;
    }
}
