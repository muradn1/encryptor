package encryptor;

/**
 * Created by murad on 05/07/2016.
 */
public interface EncryptionAlgorithm {
    void encrypt(byte key, myFile myfile);
    void decrypt(byte key, myFile myfile);
}
