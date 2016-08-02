package encryptor;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Created by murad on 02/08/2016.
 */
public class AlgorithmsModule extends AbstractModule {

    private String simpleMainAlgorithm = null;
    private String complexMainAlgorithm = null;
    private String firstSubAlgorithm = null;
    private String secondSubAlgorithm = null;
    private boolean isSimpleAlgorithm ;

    public AlgorithmsModule(String simpleMainAlgorithm) {
        this.simpleMainAlgorithm =simpleMainAlgorithm;
        this.isSimpleAlgorithm = true;
    }
    public AlgorithmsModule(String complexMainAlgorithm, String firstSubAlgorithm, String secondSubAlgorithm){
       this.complexMainAlgorithm = complexMainAlgorithm;
        this.firstSubAlgorithm =firstSubAlgorithm;
        this.secondSubAlgorithm = secondSubAlgorithm;
        this.isSimpleAlgorithm = false;
    }
    @Override
    protected void configure() {

     /*
      * This tells Guice that whenever it sees a dependency on a TransactionLog,
      * it should satisfy the dependency using a DatabaseTransactionLog.
      */
        //bind(TransactionLog.class).to(DatabaseTransactionLog.class);

     /*
      * Similarly, this binding tells Guice that when CreditCardProcessor is used in
      * a dependency, that should be satisfied with a PaypalCreditCardProcessor.
      */
       // bind(CreditCardProcessor.class).to(PaypalCreditCardProcessor.class);
        //if(isSimpleAlgorithm)
        try {
            switch (simpleMainAlgorithm) {
                case "caesar": //simpleAlgorithmInstance = new CaesarAlgorithm();
                    bind(EncryptionAlgorithm.class).to(CaesarAlgorithm.class);
                    break;
                case "xor": //simpleAlgorithmInstance = new XorAlgorithm();
                    bind(EncryptionAlgorithm.class).to(XorAlgorithm.class);
                    break;
                case "multi":// simpleAlgorithmInstance = new MultiplicationAlgorithm();
                    bind(EncryptionAlgorithm.class).to(MultiplicationAlgorithm.class);
                    break;
                default:
                    bind(EncryptionAlgorithm.class).to(EncryptionAlgorithm.class);
                    break;
            }
        }catch (NullPointerException ex){
            bind(EncryptionAlgorithm.class).to(CaesarAlgorithm.class);
        }
        //else if(!isSimpleAlgorithm) {
        try {
            switch (complexMainAlgorithm) {
                case "double": //complexAlgorithmInstance = new DoubleAlgorithm();
                    bind(EncryptionAlgorithmsWithGeneric.class).to(DoubleAlgorithm.class);
                    break;
                case "reverse": //complexAlgorithmInstance = new ReverseAlgorithm();
                    bind(EncryptionAlgorithmsWithGeneric.class).to(ReverseAlgorithm.class);
                    break;
                case "split":// complexAlgorithmInstance= new SplitAlgorithm();
                    bind(EncryptionAlgorithmsWithGeneric.class).to(SplitAlgorithm.class);
                    break;
                default:
                    bind(EncryptionAlgorithmsWithGeneric.class).to(EncryptionAlgorithmsWithGeneric.class);
                    break;
            }
        }catch (NullPointerException ex){
            bind(EncryptionAlgorithmsWithGeneric.class).to(DoubleAlgorithm.class);
        }

        try {
            switch (firstSubAlgorithm) {
                case "caesar": //simpleAlgorithmInstance = new CaesarAlgorithm();
                    bind(EncryptDecryptObservable.class).annotatedWith(Names.named("firstSubAlgorithm")).to(CaesarAlgorithm.class);
                    break;
                case "xor": //simpleAlgorithmInstance = new XorAlgorithm();
                    bind(EncryptDecryptObservable.class).annotatedWith(Names.named("firstSubAlgorithm")).to(XorAlgorithm.class);
                    break;
                case "multi":// simpleAlgorithmInstance = new MultiplicationAlgorithm();
                    bind(EncryptDecryptObservable.class).annotatedWith(Names.named("firstSubAlgorithm")).to(MultiplicationAlgorithm.class);
                    break;
                default:
                    bind(EncryptDecryptObservable.class).annotatedWith(Names.named("firstSubAlgorithm")).to(EncryptDecryptObservable.class);
                    break;
            }
        }
        catch (NullPointerException ex){
            bind(EncryptDecryptObservable.class).annotatedWith(Names.named("firstSubAlgorithm")).to(CaesarAlgorithm.class);
        }
          //  if(!secondSubAlgorithm.equals(null))
        try {

            switch (secondSubAlgorithm) {

                case "caesar": //simpleAlgorithmInstance = new CaesarAlgorithm();
                    bind(EncryptDecryptObservable.class).annotatedWith(Names.named("secondSubAlgorithm")).to(CaesarAlgorithm.class);
                    break;
                case "xor": //simpleAlgorithmInstance = new XorAlgorithm();
                    bind(EncryptDecryptObservable.class).annotatedWith(Names.named("secondSubAlgorithm")).to(XorAlgorithm.class);
                    break;
                case "multi":// simpleAlgorithmInstance = new MultiplicationAlgorithm();
                    bind(EncryptDecryptObservable.class).annotatedWith(Names.named("secondSubAlgorithm")).to(MultiplicationAlgorithm.class);
                    break;
                default:
                    bind(EncryptDecryptObservable.class).annotatedWith(Names.named("secondSubAlgorithm")).to(EncryptDecryptObservable.class);
                    break;

            }
        }catch (NullPointerException ex){
            bind(EncryptDecryptObservable.class).annotatedWith(Names.named("secondSubAlgorithm")).to(CaesarAlgorithm.class);
        }

        }

    //}
}
