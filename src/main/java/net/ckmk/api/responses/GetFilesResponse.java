package net.ckmk.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.ckmk.api.prototypes.FileEntity;
import net.ckmk.api.prototypes.SafeFileEntity;
import net.ckmk.api.prototypes.SafeUser;
import net.ckmk.api.prototypes.User;

import java.util.ArrayList;

public class GetFilesResponse extends Response{
    @JsonProperty
    ArrayList<SafeFileEntity> files;
    public GetFilesResponse(ArrayList<FileEntity> f){
        if (f != null){
            files = new ArrayList<>();
            for (FileEntity file : f){
                files.add(new SafeFileEntity(file.getId(), file.getFileName(), file.getFileSize(), file.getCreatedAt()));
            }
        } else files = null;
    }

    public ArrayList<SafeFileEntity> getFiles() {
        return files;
    }
}
