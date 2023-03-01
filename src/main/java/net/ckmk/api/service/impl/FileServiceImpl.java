package net.ckmk.api.service.impl;

import net.ckmk.api.database.DbManager;
import net.ckmk.api.database.FileManager;
import net.ckmk.api.prototypes.FileEntity;
import net.ckmk.api.requests.GetFileRequest;
import net.ckmk.api.requests.GetFilesRequest;
import net.ckmk.api.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileManager fileManager;
    @Autowired
    private DbManager dbManager;

    @Override
    public byte[] getFile(GetFileRequest request) throws IOException {
        if (dbManager.isDbEnabled() && dbManager.validateToken(request.getEmail(), request.getUat())){
            return fileManager.getFile(dbManager.getFileData(request.getEmail(), request.getFileName()).getFilePath());
        }
        return null;
    }
    @Override
    public ArrayList<FileEntity> getFiles(String email, String uat) {
        if (dbManager.isDbEnabled() && dbManager.validateToken(email, uat)){
            return dbManager.getFiles(email);
        }
        return null;
    }

    @Override
    public ArrayList<FileEntity> getFiles(String email, String uat, ArrayList<Integer> ids) {
        if (dbManager.isDbEnabled() && dbManager.validateToken(email, uat)){
            return dbManager.getFiles(email, ids);
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
            return fileManager.saveFile(email, file, dbManager);
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
