package encryptor;

/**
 * Created by murad on 08/07/2016.
 */
public class XorAlgorithm extends EncryptDecryptObservable {

    @Override
    public void encrypt(byte key, myFile myfile) {

        encrypt_decrypt_start("Encryption");
        byte[] copiedFileData = getTheFileData(myfile);

        ///////////////////////////encrypt using XOR algorithm/////////////////////////////////
        for(int i=0;i<copiedFileData.length;i++) { //encrypt the bytes in the copied byteArray
                copiedFileData[i] = applyEncrypt(key,copiedFileData[i]);
        }
        //////////////////////////////////////////////////////////////////////////////////////////

        createTheEncryptedFile(myfile,copiedFileData);
        encrypt_decrypt_end("Encryption");

    }

    @Override
    public void decrypt(byte key, myFile myfile) {
        encrypt_decrypt_start("Decryption");
        byte[] copiedFileData = getTheFileData(myfile);

        ///////////////////////////decrypt using XOR algorithm/////////////////////////////////
        for(int i=0;i<copiedFileData.length;i++) { //decrypt the bytes in the copied byteArray
                copiedFileData[i] = applyDecrypt(key,copiedFileData[i]);
        }
        //////////////////////////////////////////////////////////////////////////////////////////

        createTheDecryptedFile(myfile,copiedFileData);
        encrypt_decrypt_end("Decryption");
    }

    @Override
    public byte applyEncrypt(byte key, byte copiedByteFromFileData){
        copiedByteFromFileData = (byte)(copiedByteFromFileData ^ key);

        return copiedByteFromFileData;

    }

    @Override
    public byte applyDecrypt(byte key, byte copiedByteFromFileData){
        copiedByteFromFileData = (byte)(copiedByteFromFileData ^ key);

        return copiedByteFromFileData;
    }
}
