package encryptor;

import lombok.Setter;
import lombok.Getter;

import java.io.*;
import java.util.Random;

/**
 * Created by murad on 11/07/2016.
 */
public class Key {

    @Getter @Setter private byte[] key;
    @Getter @Setter private String pathOfKey;

    public Key(){
        key = new byte[2];
    }
    public void generateNewKey(String pathToTheKey) {
        Random random;
        int rand;
        for(int i=0;i<key.length;i++){
        do {
            random = new Random();
            rand = random.nextInt(Byte.MAX_VALUE -1)+1;
        }while (rand%2==0);
        key[i] = (byte)rand;
        }
        this.pathOfKey = pathToTheKey + "\\key.bin";

        try {
            FileOutputStream fos = new FileOutputStream(pathOfKey);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.write(key);
            os.close();
            fos.close();
            System.out.println("the path of the key is: "+ this.pathOfKey);
            System.out.println("the first key is: "+key[0] +"\nthe second key is: " + key[1]);

        }
        catch(Exception e) {
            System.out.println("error in creating/writing to new key.ben");
            e.printStackTrace();
        }

    }

    public void getTheKeyFromPath(String pathOfTheKey) throws IllegalKeyException{

        try
        {
            FileInputStream fileIn = new FileInputStream(pathOfTheKey);
            ObjectInputStream is = new ObjectInputStream(fileIn);
            is.read(key);
            System.out.println("the first key is: "+key[0] +"\nthe second key is: " + key[1]);
            this.pathOfKey = pathOfTheKey;
            is.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            throw new IllegalKeyException();
        }


    }


}
