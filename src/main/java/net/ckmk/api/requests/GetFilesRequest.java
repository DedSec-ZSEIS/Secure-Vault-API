package net.ckmk.api.requests;

import java.util.ArrayList;

public class GetFilesRequest extends RequestValidator {
    private String email;
    private ArrayList<Integer> fileIds;

    public ArrayList<Integer> getFileIds() {
        return fileIds;
    }

    public void setFileIds(ArrayList<Integer> fileIds) {
        this.fileIds = fileIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
