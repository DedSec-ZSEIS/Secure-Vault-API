package net.ckmk.api.service;

import net.ckmk.api.requests.GetFileRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    byte[] getFile(GetFileRequest request) throws IOException;
    boolean removeFile();
    boolean saveFile(String email, String uat, MultipartFile file);
    boolean createUserFolder(String email, String uat);
}
