package encryptor;

import java.util.concurrent.Executor;

/**
 * Created by murad on 15/07/2016.
 */
public class threadPerTaskExecutor implements Executor {
    @Override
    public void execute(Runnable r) {
        new Thread(r).start();
    }
}
