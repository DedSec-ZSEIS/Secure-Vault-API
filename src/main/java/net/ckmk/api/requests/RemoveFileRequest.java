package net.ckmk.api.requests;

public class RemoveFileRequest extends RequestValidator{
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
