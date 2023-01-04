package net.ckmk.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.ckmk.api.database.DbManager;

public class ValidateGenerationLinkResponse extends Response{
    private final DbManager db = new DbManager();
    @JsonProperty
    private final boolean exists;
    @JsonProperty
    private final String uat;
    @JsonProperty
    private String email;

    public ValidateGenerationLinkResponse(String uat) {
        this.uat = uat;
        if (db.isDbEnabled()){
            exists = validateLink(uat);
            if (exists){
                this.email = db.getEmail(uat);
            }
        } else exists = false;
    }

    private boolean validateLink(String uat){
        boolean succ = db.validateGenerationLink(uat);
        if (succ){
            setSuccessful(true);
        } else setSuccessful(false);
        return succ;
    }
}
