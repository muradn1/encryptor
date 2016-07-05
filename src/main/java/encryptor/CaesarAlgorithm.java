package encryptor;

import java.io.FileOutputStream;

/**
 * Created by murad on 05/07/2016.
 */
public class CaesarAlgorithm implements EncryptionAlgorithm {


    public void encrypt(byte key, myFile myfile) {

        byte[] copiedFileData = new byte[myfile.getFileData().length]; //copying the byteArray
        for(int i=0; i < copiedFileData.length; i++) {
            copiedFileData[i] =myfile.getFileData()[i];
        }

        for(int i=0;i<copiedFileData.length;i++) { //encrypt the bytes in the copied byteArray
            if(copiedFileData[i] + key > Byte.MAX_VALUE) {
                int temp = (Byte.MIN_VALUE-1)+(copiedFileData[i] + key - Byte.MAX_VALUE);//127+1 := -128 <=> -129 + ((127+1)-127)
                copiedFileData[i] = (byte)temp;
            }
            else
                copiedFileData[i] = (byte)(copiedFileData[i] + key);
        }


        String newEncryptedFileName = myfile.getFileFullPath()+"."+"encrypted";
        //System.out.println(newEncryptedFileName);
        try {
            FileOutputStream fos = new FileOutputStream(newEncryptedFileName);
            fos.write(copiedFileData);
            fos.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("the encryption of the file is DONE!!");
    }

    public void decrypt(byte key, myFile myfile) {
        byte[] copiedFileData = new byte[myfile.getFileData().length]; //copying the byteArray
        for(int i=0; i < copiedFileData.length; i++) {
            copiedFileData[i] =myfile.getFileData()[i];
        }

        for(int i=0;i<copiedFileData.length;i++) { //encrypt the bytes in the copied byteArray
            if(copiedFileData[i] - key < Byte.MIN_VALUE) {
                int temp = (copiedFileData[i] - key + Byte.MAX_VALUE)-(Byte.MIN_VALUE-1);//
                copiedFileData[i] = (byte)temp;
            }
            else
                copiedFileData[i] = (byte)(copiedFileData[i] - key);
        }
        myfile.setExtension(myfile.getFileName());
        //System.out.println(myfile.getExtension());
        myfile.setFileName(myfile.getFilePath()+"\\"+myfile.getFileName());
        //System.out.println(myfile.getFileName());
        //System.out.println(myfile.getFilePath());
        String newDecryptedFileName = myfile.getFilePath()+"\\"+myfile.getFileName()+"_decrypted"+"."+myfile.getExtension();

        try {
            FileOutputStream fos = new FileOutputStream(newDecryptedFileName);
            fos.write(copiedFileData);
            fos.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("the Decryption of the file is DONE!!");

    }
}
