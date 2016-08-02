package encryptor;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.Getter;

/**
 * Created by murad on 01/08/2016.
 */
public class EncryptionAlgorithms {

    @Getter private EncryptionAlgorithm simpleMainEncryptionAlgorithmInstance = null;
    @Named("firstSubAlgorithm")
    @Getter private EncryptDecryptObservable firstSubAlgorithmInstance = null;
    @Named("secondSubAlgorithm")
    @Getter private EncryptDecryptObservable secondSubAlgorithmInstance = null;
    @Getter private EncryptionAlgorithmsWithGeneric complexMainEncryptionAlgorithmInstance = null;

    @Inject
    EncryptionAlgorithms(EncryptionAlgorithm simpleMainEncryptionAlgorithmInstance,
                         @Named("firstSubAlgorithm")
                         EncryptDecryptObservable firstSubAlgorithmInstance,
                         @Named("secondSubAlgorithm")
                         EncryptDecryptObservable secondSubAlgorithmInstance,
                         EncryptionAlgorithmsWithGeneric complexMainEncryptionAlgorithmInstance) {
        this.simpleMainEncryptionAlgorithmInstance = simpleMainEncryptionAlgorithmInstance;

        this.firstSubAlgorithmInstance = firstSubAlgorithmInstance;
        this.secondSubAlgorithmInstance = secondSubAlgorithmInstance;
        this.complexMainEncryptionAlgorithmInstance = complexMainEncryptionAlgorithmInstance;
    }


    public EncryptionAlgorithms(String simpleMainAlgorithm){
        simpleMainEncryptionAlgorithmInstance = getSimpleAlgorithmInstance(simpleMainAlgorithm);
    }

    public EncryptionAlgorithms(String complexMainAlgorithm, String firstSubAlgorithm, String secondSubAlgorithm){
        complexMainEncryptionAlgorithmInstance = getComplexAlgorithmInstance(complexMainAlgorithm);
        this.firstSubAlgorithmInstance = (EncryptDecryptObservable)getSimpleAlgorithmInstance(firstSubAlgorithm);
        if(!secondSubAlgorithm.equals(null))
            this.secondSubAlgorithmInstance = (EncryptDecryptObservable)getSimpleAlgorithmInstance(secondSubAlgorithm);
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
