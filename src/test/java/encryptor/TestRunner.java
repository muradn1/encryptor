package encryptor;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
/**
 * Created by murad on 03/07/2016.
 */
public class TestRunner {

    public static void runClassTests (java.lang.Class<?> class1) {
        Result result = JUnitCore.runClasses(class1);
        int i = result.getRunCount();
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        String nameOfTheClass = class1.getSimpleName().substring(0,class1.getSimpleName().indexOf("Test"));
        System.out.println("All the " + i + " tests of " + nameOfTheClass +" class were Successful: "+ result.wasSuccessful());
    }

    public static void main(String[] args) {

        //running the tests of the Helper class
        runClassTests(HelperTest.class);

        //running the tests of the CaesarAlgorithm class
        runClassTests(CaesarAlgorithmTest.class);

        //running the tests of the XorAlgorithm class
        runClassTests(XorAlgorithmTest.class);

        //running the tests of the MultiplicationAlgorithm class
        runClassTests(MultiplicationAlgorithmTest.class);

        //running the tests of the EncryptDecryptObserver class
        runClassTests(EncryptDecryptObserverTest.class);

        //running the tests of the DoubleAlgorithm class
        runClassTests(DoubleAlgorithmTest.class);

        //running the tests of the ReverseAlgorithm class
        runClassTests(ReverseAlgorithmTest.class);

        //running the tests of the SplitAlgorithm class
        runClassTests(SplitAlgorithmTest.class);


    }





}
