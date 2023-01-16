package net.ckmk.api.database;

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

    public boolean removeFile(){
        return false;
    }

    public boolean saveFile(String email, MultipartFile file) {
        createUserFolder(email);
        String path = folderPath + "\\" + email + "\\" + file.getOriginalFilename();
        File f = new File(path);
        try{
            file.transferTo(f);
        } catch (IOException e){
            return false;
        }
        return true;
    }

    public boolean createUserFolder(String email){
        String newPath = folderPath + "\\" + email;
        File dir = new File(newPath);
        if (dir.exists()) return false;
        return dir.mkdir();
    }
}
