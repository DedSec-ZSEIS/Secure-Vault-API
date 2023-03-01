package net.ckmk.api.prototypes;

public class FileEntity extends SafeFileEntity{
    private User owner;
    private String filePath;

    public FileEntity(int id, User owner, String fileName, String filePath, long fileSize, String createdAt) {
        super(id, fileName, fileSize, createdAt);
        setOwner(owner);
        setFilePath(filePath);
    }

    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
