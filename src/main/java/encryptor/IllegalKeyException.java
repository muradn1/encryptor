package encryptor;

/**
 * Created by murad on 08/07/2016.
 */
public class IllegalKeyException extends Exception {
    public IllegalKeyException(){

    }

    public IllegalKeyException(String message){
        super(message);
    }
}
