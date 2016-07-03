package encryptor;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
/**
 * Created by murad on 03/07/2016.
 */
public class TestRunner {
    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(HelperTest.class);
        int i = result.getRunCount();
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("the test of Helper.getInputFromUser method was Successful: " + result.wasSuccessful() );
        System.out.println("All the " + i + " tests of helper class were Successful");
    }

}
