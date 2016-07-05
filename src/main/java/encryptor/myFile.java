package encryptor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import lombok.Setter;
import lombok.Getter;

/**
 * Created by murad on 01/07/2016.
 */

public class myFile {
    @Getter private String fileName;
    @Getter @Setter private String fileFullPath;
    @Getter private String filePath;
    @Getter private byte[] fileData;
    @Getter private String extension;


    public myFile(String path){
        this.fileFullPath =path;
        setFileName(path);
        setFilePath(path);
        setFileData(path);
        setExtension(path);
       // System.out.println("fullPath = "+fileFullPath+"\nname = "+ fileName +"\nextension = "+extension +"\npath = " + filePath  );
    }

    public void setFileName(String path) {
        File tempFile = new File(path);
        String name = tempFile.getName();
        int pos = name.lastIndexOf('.');
        if(pos > -1)
            name = name.substring(0,pos);
        fileName = name;
    }

    public void setFilePath(String path) {
        filePath = path.substring(0,path.lastIndexOf(File.separator));
    }

    public void setFileData(String path)    {
        Path filePath = Paths.get(path);
        try {
            fileData = Files.readAllBytes(filePath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setExtension(String path) {
        int i = path.lastIndexOf('.');
        if(i>0)
            extension = path.substring(i+1);
        else
            extension = null;

    }
}
