package encryptor;

import javax.imageio.IIOException;
import java.io.*;
import java.util.Arrays;
/**
 * Created by murad on 01/07/2016.
 */
public class myFile { 
    private String fileName;
    private String filePath;
    private char[] fileData;
    private String fileDecryption = null;
    private String fileEncryption = null;

    public myFile(String name,String path){
        setFileName(name);
        setFilePath(path);
        setFileData(path);
    }
    public String getFileName() {
        return this.fileName;
    }
    public String getFilePath() {
        return  this.filePath;
    }

    public char[] getFileData() {
        return fileData;
    }
    public String getFileDecryption() {
        return this.fileDecryption;
    }
    public String getFileEncryption() {
        return  this.fileEncryption;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileData(String path)
    {
        String Data = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            br.close();
            Data = sb.toString();
        }
        catch(IOException e) {

        }
        //System.out.println(Data);
        this.fileData = Data.toCharArray();

        for(char ch:fileData) {
            if (ch != '\n' && ch != ' ')
                ch = (char) (ch + 5);
            //System.out.println(ch);
        }
    }

    public String encryptFile() {
        char[] fileEncrypt = new char[fileData.length];
        for(int i=0;i<fileData.length;i++) {
            if (fileData[i] != '\n' && fileData[i] != ' ')
                fileEncrypt[i] = (char) (fileData[i] + 5);
            else
                fileEncrypt[i] = fileData[i];
            //System.out.println(ch);
        }
        fileEncryption = String.valueOf(fileEncrypt);
        return fileEncryption;
    }

    public String decryptFile() {
        if(fileEncryption == null) //if we didn't encrypt the file yet
            return String.valueOf(fileData);
        else {
            char[] fileEncrypt = fileEncryption.toCharArray();
            char[] fileDecrypt = new char[fileEncrypt.length];
            for (int i = 0; i < fileEncrypt.length; i++) {
                if (fileEncrypt[i] != '\n' && fileEncrypt[i] != ' ')
                    fileDecrypt[i] = (char) (fileEncrypt[i] - 5);
                else
                    fileDecrypt[i] = fileEncrypt[i];
                //System.out.println(ch);
            }
            fileDecryption = String.valueOf(fileDecrypt);
            return fileDecryption;
        }
    }
}
