package encryptor;

/**
 * Created by murad on 09/07/2016.
 */
public class MultiplicationAlgorithm extends EncryptDecryptObservable {

    @Override
    public void encrypt(byte key, myFile myfile) {

        encrypt_decrypt_start("Encryption");
        byte[] copiedFileData = getTheFileData(myfile);

        ///////////////////////////encrypt using Multiplication algorithm/////////////////////////////////
        for(int i=0;i<copiedFileData.length;i++) { //encrypt the bytes in the copied byteArray
            copiedFileData[i] = (byte)(copiedFileData[i] * key);
        }
        //////////////////////////////////////////////////////////////////////////////////////////

        createTheEncryptedFile(myfile,copiedFileData);
        encrypt_decrypt_end("Encryption");
    }

    @Override
    public void decrypt(byte key, myFile myfile) {

        encrypt_decrypt_start("Decryption");
        byte[] copiedFileData = getTheFileData(myfile);
        byte decryptionKey=1;
        ///////////////////////////decrypt using Multiplication algorithm/////////////////////////////////

        //finding the decryption key
        for(byte bt = Byte.MIN_VALUE; bt<=Byte.MAX_VALUE; bt++) {
            if ((byte)(key*bt)==1) {
                decryptionKey = bt;
                break;
            }
        }

        for(int i=0;i<copiedFileData.length;i++) { //decrypt the bytes in the copied byteArray
            copiedFileData[i] = (byte)(copiedFileData[i] * decryptionKey);
        }
        //////////////////////////////////////////////////////////////////////////////////////////

        createTheDecryptedFile(myfile,copiedFileData);
        encrypt_decrypt_end("Decryption");

    }
}
