package encryptor;

/**
 * Created by murad on 05/07/2016.
 */
public interface EncryptionAlgorithm {
    void encrypt(byte key, MyFile myfile) throws Exception;
    void decrypt(byte key, MyFile myfile) throws Exception;
    byte applyEncrypt(byte key, byte copiedByteFromFileData);
    byte applyDecrypt(byte key, byte copiedByteFromFileData);
}
