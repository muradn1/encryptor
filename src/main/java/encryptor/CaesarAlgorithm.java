package encryptor;

import java.io.FileOutputStream;

/**
 * Created by murad on 05/07/2016.
 */
public class CaesarAlgorithm extends EncryptDecryptObservable {


    public void encrypt(byte key, myFile myfile) {
        encrypt_decrypt_start("Encryption");

        byte[] copiedFileData = getTheFileData(myfile);

        ///////////////////////////encrypt using Caesar algorithm/////////////////////////////////
        for(int i=0;i<copiedFileData.length;i++) { //encrypt the bytes in the copied byteArray
            if(copiedFileData[i] + key > Byte.MAX_VALUE) {
                int temp = (Byte.MIN_VALUE-1)+(copiedFileData[i] + key - Byte.MAX_VALUE);//127+1 := -128 <=> -129 + ((127+1)-127)
                copiedFileData[i] = (byte)temp;
            }
            else
                copiedFileData[i] = (byte)(copiedFileData[i] + key);
        }
        //////////////////////////////////////////////////////////////////////////////////////////

        createTheEncryptedFile(myfile,copiedFileData);

        //System.out.println("the encryption of the file is DONE!!");
        encrypt_decrypt_end("Encryption");
    }




    public void decrypt(byte key, myFile myfile) {

        encrypt_decrypt_start("Decryption");

        byte[] copiedFileData = getTheFileData(myfile);

        ///////////////////////////decrypt using Caesar algorithm/////////////////////////////////
        for(int i=0;i<copiedFileData.length;i++) { //decrypt the bytes in the copied byteArray
            if(copiedFileData[i] - key < Byte.MIN_VALUE) {
                int temp = (copiedFileData[i] - key + Byte.MAX_VALUE)-(Byte.MIN_VALUE-1);//
                copiedFileData[i] = (byte)temp;
            }
            else
                copiedFileData[i] = (byte)(copiedFileData[i] - key);
        }
        //////////////////////////////////////////////////////////////////////////////////////////

        createTheDecryptedFile(myfile,copiedFileData);

        //System.out.println("the Decryption of the file is DONE!!"); 23
        encrypt_decrypt_end("Decryption");
    }
}
