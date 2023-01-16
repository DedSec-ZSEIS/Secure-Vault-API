package net.ckmk.api.service.impl;

import net.ckmk.api.database.DbManager;
import net.ckmk.api.database.FileManager;
import net.ckmk.api.requests.GetFileRequest;
import net.ckmk.api.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileManager fileManager;
    @Autowired
    private DbManager dbManager;

    //Todo to sie jeszcze pozmienia
    @Override
    public byte[] getFile(GetFileRequest request) throws IOException {
        if (dbManager.isDbEnabled() && dbManager.validateToken(request.getEmail(), request.getUat())){
            return fileManager.getFile("root@root.net\\tak.txt");
        }
        return null;
    }

    @Override
    public boolean removeFile() {
        if (dbManager.isDbEnabled()){
            return fileManager.removeFile();
        }
        return false;
    }

    @Override
    public boolean saveFile(String email, String uat, MultipartFile file) {
        if (dbManager.isDbEnabled() && dbManager.validateToken(email, uat)){
            return fileManager.saveFile(email, file);
        }
        return false;
    }

    @Override
    public boolean createUserFolder(String email, String uat) {
        if (dbManager.isDbEnabled() && dbManager.validateToken(email, uat)){
            return fileManager.createUserFolder(email);
        }
        return false;
    }
}
