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

        String[] nameAndMessage = (String[])arg;
        System.out.println(nameAndMessage[1]);
        if(nameAndMessage[1].contains("start")) {
            //startedTime = Calendar.getInstance().getTimeInMillis();
            startedTime = System.nanoTime();

        }
        else {
           // elapsedTime = Calendar.getInstance().getTimeInMillis() - startedTime;
            elapsedTime = System.nanoTime()-startedTime;
            System.out.printf("the time which the file: "+nameAndMessage[0] +" took (in nanos): %,d\n",elapsedTime);
        }

    }
}
