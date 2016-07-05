package encryptor;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
/**
 * Created by murad on 03/07/2016.
 */
public class TestRunner {
    public static void main(String[] args) {

        //running the tests of the Helper class
        Result result = JUnitCore.runClasses(HelperTest.class);
        int i = result.getRunCount();
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

            System.out.println("All the " + i + " tests of helper class were Successful: "+ result.wasSuccessful());

        //running the tests of the CaesarAlgorithm class
        result = JUnitCore.runClasses(CaesarAlgorithmTest.class);
        i = result.getRunCount();
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("\nAll the " + i + " tests of CaesarAlgorithm class were Successful: "+ result.wasSuccessful());


    }




}
