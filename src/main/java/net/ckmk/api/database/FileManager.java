package net.ckmk.api.database;

import net.ckmk.api.prototypes.FileEntity;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private final String folderPath;

    public FileManager(String folderName){
        Path currentPath = Paths.get("").toAbsolutePath();
        System.out.println("Working in " + currentPath);
        folderPath = currentPath + "\\" + folderName;
        File dir = new File(folderPath);
        if (dir.exists()){
            return;
        }
        dir.mkdir();
    }
    //Todo file stuff

    public byte[] getFile(String fileDir) throws IOException{
        String path = folderPath + "\\" + fileDir;
        InputStream in = new FileInputStream(path);
        return IOUtils.toByteArray(in);
    }

    public boolean removeFile(FileEntity fileData, DbManager dbManager){
        String p = folderPath + fileData.getFilePath();
        File f = new File(p);
        if (!f.delete()){
            return false;
        }
        dbManager.removeFile(fileData.getId());
        return true;
    }

    public boolean saveFile(String email, MultipartFile file, DbManager dbManager) {
        createUserFolder(email);
        String shortPath = "\\" + email + "\\" + file.getOriginalFilename();
        String path = folderPath + shortPath;
        File f = new File(path);
        try{
            file.transferTo(f);
        } catch (IOException e){
            return false;
        }
        dbManager.saveFile(email, shortPath, file.getOriginalFilename(), file.getSize());
        return true;
    }

    public boolean createUserFolder(String email){
        String newPath = folderPath + "\\" + email;
        File dir = new File(newPath);
        if (dir.exists()) return false;
        return dir.mkdir();
    }
}
