package encryptor;

/**
 * Created by murad on 11/07/2016.
 */
 interface EncryptionAlgorithmsWithGeneric {
    <Alg1 extends EncryptDecryptObservable,Alg2 extends EncryptDecryptObservable> void encrypt(Key key, MyFile myfile, Alg1 algorithm1, Alg2 algorithm2);
    <Alg1 extends EncryptDecryptObservable,Alg2 extends EncryptDecryptObservable> void decrypt(Key key, MyFile myfile, Alg1 algorithm1, Alg2 algorithm2);
}
