package net.ckmk.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.ckmk.api.database.DbManager;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidateGenerationLinkResponse extends Response{
    @Autowired
    private DbManager db;
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
        return db.validateGenerationLink(uat);
    }
}
