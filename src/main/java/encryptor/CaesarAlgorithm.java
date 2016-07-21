package encryptor;

import java.io.IOException;

/**
 * Created by murad on 05/07/2016.
 */
public class CaesarAlgorithm extends EncryptDecryptObservable {

    @Override
    public void encrypt(byte key, MyFile myfile) throws Exception {
        encrypt_decrypt_start("Encryption",myfile.getFileName()+"."+myfile.getExtension());

        byte[] copiedFileData = getTheFileData(myfile);

        ///////////////////////////encrypt using Caesar algorithm/////////////////////////////////
        for(int i=0;i<copiedFileData.length;i++) { //encrypt the bytes in the copied byteArray
            copiedFileData[i] = applyEncrypt(key,copiedFileData[i]);
        }
        //////////////////////////////////////////////////////////////////////////////////////////

        createTheEncryptedFile(myfile,copiedFileData);

        //System.out.println("the encryption of the file is DONE!!");
        encrypt_decrypt_end("Encryption",myfile.getFileName()+"."+myfile.getExtension());
    }



    @Override
    public void decrypt(byte key, MyFile myfile) throws Exception{

        encrypt_decrypt_start("Decryption",myfile.getFileName()+"."+myfile.getExtension());

        byte[] copiedFileData = getTheFileData(myfile);

        ///////////////////////////decrypt using Caesar algorithm/////////////////////////////////
        for(int i=0;i<copiedFileData.length;i++) { //decrypt the bytes in the copied byteArray
                copiedFileData[i] = applyDecrypt(key,copiedFileData[i]);
        }
        //////////////////////////////////////////////////////////////////////////////////////////

        createTheDecryptedFile(myfile,copiedFileData);

        //System.out.println("the Decryption of the file is DONE!!"); 23
        encrypt_decrypt_end("Decryption",myfile.getFileName()+"."+myfile.getExtension());
    }

    @Override
    public byte applyEncrypt (byte key,byte copiedByteFromFileData) {
        if(copiedByteFromFileData + key > Byte.MAX_VALUE) {
            int temp = (Byte.MIN_VALUE-1)+(copiedByteFromFileData + key - Byte.MAX_VALUE);//127+1 := -128 <=> -129 + ((127+1)-127)
            copiedByteFromFileData = (byte)temp;
        }
        else
            copiedByteFromFileData = (byte)(copiedByteFromFileData + key);

        return copiedByteFromFileData;
    }

    @Override
    public byte applyDecrypt(byte key,byte copiedByteFromFileData){
        if(copiedByteFromFileData - key < Byte.MIN_VALUE) {
            int temp = (copiedByteFromFileData - key + Byte.MAX_VALUE)-(Byte.MIN_VALUE-1);//
            copiedByteFromFileData = (byte)temp;
        }
        else
            copiedByteFromFileData = (byte)(copiedByteFromFileData - key);

        return copiedByteFromFileData;
    }

}
