package net.ckmk.api.service;

import net.ckmk.api.prototypes.FileEntity;
import net.ckmk.api.requests.GetFileRequest;
import net.ckmk.api.requests.GetFilesRequest;
import net.ckmk.api.requests.RemoveFileRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

public interface FileService {
    public ArrayList<FileEntity> getFiles(String email, String uat);
    public ArrayList<FileEntity> getFiles(String email, String uat, ArrayList<Integer> ids);
    byte[] getFile(GetFileRequest request) throws IOException;
    boolean removeFile(RemoveFileRequest request);
    boolean saveFile(String email, String uat, MultipartFile file);
    boolean createUserFolder(String email, String uat);
}
