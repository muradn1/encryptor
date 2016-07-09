package encryptor;

/**
 * Created by murad on 08/07/2016.
 */
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Observable;
import java.util.Observer;
import java.util.Date;
import java.util.Calendar;

public class EncryptDecryptObserver implements Observer{

    private long startedTime;
    private long elapsedTime;

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
        String args = (String)arg;
        if(args.contains("start")) {
            startedTime = Calendar.getInstance().getTimeInMillis();

        }
        else {
            elapsedTime = Calendar.getInstance().getTimeInMillis() - startedTime;
            System.out.println("the time which the whole process took (in millis): " + elapsedTime);
        }

    }
}
