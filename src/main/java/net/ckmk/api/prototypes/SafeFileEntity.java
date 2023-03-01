package net.ckmk.api.prototypes;

public class SafeFileEntity {
    private int id;
    private String fileName;
    private long fileSize;
    private String createdAt;

    public SafeFileEntity(int id, String fileName, long fileSize, String createdAt) {
        setId(id);
        setFileName(fileName);
        setFileSize(fileSize);
        setCreatedAt(createdAt);
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
