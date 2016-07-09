package encryptor;
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


    public void encrypt_decrypt_start(String act) {
        setChanged();
        notifyObservers("the " + act + " of " + this.getClass().getSimpleName() + " has started");
    }

    public void encrypt_decrypt_end(String act){
        setChanged();
        notifyObservers("the " + act + " of " + this.getClass().getSimpleName() + " is done");

    }

    public byte[] getTheFileData(myFile myfile) {

        byte[] copiedFileData = new byte[myfile.getFileData().length]; //copying the byteArray
        for(int i=0; i < copiedFileData.length; i++) {
            copiedFileData[i] =myfile.getFileData()[i];
        }
        return copiedFileData;
    }

    public void createTheEncryptedFile(myFile myfile,byte[] copiedFileData) {

        String newEncryptedFileName = myfile.getFileFullPath()+"."+"encrypted";

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

    public void createTheDecryptedFile(myFile myfile,byte[] copiedFileData) {

        myfile.setExtension(myfile.getFileName());
        myfile.setFileName(myfile.getFilePath()+"\\"+myfile.getFileName());

        String newDecryptedFileName = myfile.getFilePath()+"\\"+myfile.getFileName()+"_decrypted"+"."+myfile.getExtension();

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
