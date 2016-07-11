package encryptor;

/**
 * Created by murad on 11/07/2016.
 */
public class DoubleAlgorithm extends EncryptDecryptObservable implements EncryptionAlgorithmsWithGeneric{

    @Override
    public <Alg1 extends EncryptDecryptObservable ,Alg2 extends EncryptDecryptObservable> void encrypt(Key key, myFile myfile, Alg1 algorithm1, Alg2 algorithm2){

        encrypt_decrypt_start("Encryption");


        byte key1 = key.getKey()[0];
        byte key2 = key.getKey()[1];
        byte[] copiedFileData = getTheFileData(myfile);

        ///////////////////////////encrypt using XOR algorithm/////////////////////////////////
        for(int i=0;i<copiedFileData.length;i++) { //encrypt the bytes in the copied byteArray
            copiedFileData[i] = algorithm1.applyEncrypt(key1,copiedFileData[i]);
            copiedFileData[i] = algorithm2.applyEncrypt(key2,copiedFileData[i]);
        }
        //////////////////////////////////////////////////////////////////////////////////////////

        createTheEncryptedFile(myfile,copiedFileData);
        encrypt_decrypt_end("Encryption");

    }

    @Override
    public <Alg1 extends EncryptDecryptObservable,Alg2 extends EncryptDecryptObservable> void decrypt(Key key, myFile myfile, Alg1 algorithm1, Alg2 algorithm2){

        encrypt_decrypt_start("Decryption");

        byte key1 = key.getKey()[0];
        byte key2 = key.getKey()[1];
        byte[] copiedFileData = getTheFileData(myfile);

        ///////////////////////////decrypt using XOR algorithm/////////////////////////////////
        for(int i=0;i<copiedFileData.length;i++) { //decrypt the bytes in the copied byteArray
            copiedFileData[i] = algorithm2.applyDecrypt(key2,copiedFileData[i]);
            copiedFileData[i] = algorithm1.applyDecrypt(key1,copiedFileData[i]);
        }
        //////////////////////////////////////////////////////////////////////////////////////////

        createTheDecryptedFile(myfile,copiedFileData);
        encrypt_decrypt_end("Decryption");
    }


    @Override
    public void encrypt(byte key, myFile myfile) {

    }

    @Override
    public void decrypt(byte key, myFile myfile) {

    }

    @Override
    public byte applyEncrypt(byte key, byte copiedByteFromFileData) {
        return 0;
    }

    @Override
    public byte applyDecrypt(byte key, byte copiedByteFromFileData) {
        return 0;
    }
}
