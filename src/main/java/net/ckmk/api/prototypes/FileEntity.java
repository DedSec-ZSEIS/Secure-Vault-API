package net.ckmk.api.prototypes;

public class FileEntity {
    private final int id;
    private final User owner;
    private final String fileName;
    private final String filePath;
    private final long fileSize;
    private final String createdAt;

    public FileEntity(int id, User owner, String fileName, String filePath, long fileSize, String createdAt) {
        this.id = id;
        this.owner = owner;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
