package encryptor;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Observable;

/**
 * Created by murad on 08/07/2016.
 */
public abstract class EncryptDecryptObservable extends Observable implements EncryptionAlgorithm {

    private EncryptDecryptObserver to;

    public EncryptDecryptObservable() {
        to = new EncryptDecryptObserver();
        this.addObserver(to);
    }


    public void encrypt_decrypt_start(String act,String fileName) {
        String[] nameAndMessage = new String[2];
        nameAndMessage[0] = fileName;
        nameAndMessage[1] = "the " + act + " of " + this.getClass().getSimpleName() +" on the file: " + fileName + " has started";
        setChanged();
        notifyObservers(nameAndMessage);
    }

    public void encrypt_decrypt_end(String act,String fileName){
        String[] nameAndMessage = new String[2];
        nameAndMessage[0] = fileName;
        nameAndMessage[1] = "the " + act + " of " + this.getClass().getSimpleName() +" on the file: " + fileName + " is done";
        setChanged();
        notifyObservers(nameAndMessage);

    }

    public byte[] getTheFileData(MyFile myfile) {

        byte[] copiedFileData = new byte[myfile.getFileData().length]; //copying the byteArray
        for(int i=0; i < copiedFileData.length; i++) {
            copiedFileData[i] =myfile.getFileData()[i];
        }
        return copiedFileData;
    }

    public void createTheEncryptedFile(MyFile myfile, byte[] copiedFileData) {

        String newEncryptedFileName = myfile.getFileFullPath()+"."+"encrypted";
        File encryptedDir = new File(myfile.getFilePath()+"\\encrypted");
        if(encryptedDir.exists())
            newEncryptedFileName = myfile.getFilePath()+"\\encrypted\\"+myfile.getFileName()+"."+myfile.getExtension()+"."+"encrypted";

        try {
            FileOutputStream fos = new FileOutputStream(newEncryptedFileName);
            fos.write(copiedFileData);
            fos.close();
        }
        catch(Exception e) {
            System.out.println("error in creating/writing to new file.encrypted");
            e.printStackTrace();
        }
    }

    public String getExtensionForDecryptedFile(String FullPath){
        String extension="";
        String theNameWithEncryptedExtensions;
        String[] splitTokens = FullPath.split("\\\\");

        theNameWithEncryptedExtensions = splitTokens[splitTokens.length-1];
        splitTokens = theNameWithEncryptedExtensions.split("\\.");

        for(int i=1;i<splitTokens.length-1;i++)
            extension = extension + splitTokens[i];

        return extension;
    }

    public String getRealNameForDecryptedFile(String fileName){
        String name = "";

        String[] splitTokens = fileName.split("\\.");
        name = splitTokens[0];

        return name;
    }

    public void createTheDecryptedFile(MyFile myfile, byte[] copiedFileData) {

        String newExtension =  getExtensionForDecryptedFile(myfile.getFileFullPath());
        String newName = getRealNameForDecryptedFile(myfile.getFileName());


        String newDecryptedFileName = myfile.getFilePath()+"\\"+newName+"_decrypted"+"."+newExtension;

        File decryptedDir = new File(myfile.getFilePath()+"\\decrypted");
        if(decryptedDir.exists())
            newDecryptedFileName = myfile.getFilePath()+"\\decrypted\\"+newName+"_decrypted"+"."+newExtension;

        try {
            FileOutputStream fos = new FileOutputStream(newDecryptedFileName);
            fos.write(copiedFileData);
            fos.close();
        }
        catch(Exception e) {
            System.out.println("error in creating/writing to new file_decrypted.extension");
            e.printStackTrace();
        }
    }
}
