package encryptor;

import lombok.Getter;

/**
 * Created by murad on 01/08/2016.
 */
public class EncryptionAlgorithms {

    @Getter private EncryptionAlgorithm simpleMainEncryptionAlgorithmInstance = null;
    @Getter private EncryptDecryptObservable firstSubAlgorithmInstance = null;
    @Getter private EncryptDecryptObservable secondSubAlgorithmInstance = null;
    @Getter private EncryptionAlgorithmsWithGeneric complexMainEncryptionAlgorithmInstance = null;

    public EncryptionAlgorithms(String simpleMainAlgorithm){
        simpleMainEncryptionAlgorithmInstance = getSimpleAlgorithmInstance(simpleMainAlgorithm);
    }

    public EncryptionAlgorithms(String complexMainAlgorithm, String firstSubAlgorithmInstance, String secondSubAlgorithmInstance){
        complexMainEncryptionAlgorithmInstance = getComplexAlgorithmInstance(complexMainAlgorithm);
        this.firstSubAlgorithmInstance = (EncryptDecryptObservable)getSimpleAlgorithmInstance(firstSubAlgorithmInstance);
        if(!secondSubAlgorithmInstance.equals(null))
            this.secondSubAlgorithmInstance = (EncryptDecryptObservable)getSimpleAlgorithmInstance(secondSubAlgorithmInstance);
    }

    public EncryptionAlgorithm getSimpleAlgorithmInstance(String encryptionAlgorithmChosen){
        EncryptionAlgorithm simpleAlgorithmInstance;

        switch (encryptionAlgorithmChosen){
            case "caesar": simpleAlgorithmInstance = new CaesarAlgorithm();
                break;
            case "xor": simpleAlgorithmInstance = new XorAlgorithm();
                break;
            case "multi": simpleAlgorithmInstance = new MultiplicationAlgorithm();
                break;
            default:
                simpleAlgorithmInstance = null;
                break;
        }

        return simpleAlgorithmInstance;
    }

    public EncryptionAlgorithmsWithGeneric getComplexAlgorithmInstance(String encryptionAlgorithmChosen) {

        EncryptionAlgorithmsWithGeneric complexAlgorithmInstance;

        switch (encryptionAlgorithmChosen){
            case "double": complexAlgorithmInstance = new DoubleAlgorithm();
                break;
            case "reverse": complexAlgorithmInstance = new ReverseAlgorithm();
                break;
            case "split": complexAlgorithmInstance= new SplitAlgorithm();
                break;
            default:
                complexAlgorithmInstance = null;
                break;
        }

        return complexAlgorithmInstance;
    }


}
