package net.ckmk.api.other;

import org.springframework.context.annotation.Bean;

public class Env {
    private String frontEndUrl;

    public Env(String frontEndUrl){
        this.frontEndUrl = frontEndUrl;
    }

    public String getFrontEndUrl() {
        return frontEndUrl;
    }

    public void setFrontEndUrl(String frontEndUrl) {
        this.frontEndUrl = frontEndUrl;
    }
}
