package encryptor;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class App {
    public static void main( String[] args ) {
        ArrayList<MyFile> myFiles = new ArrayList<MyFile>();
        String act, encryptionAlgorithmChosen;
        int idx;
        boolean isDir;
        boolean isSync = false;
        String keyPath;
        Key key = new Key();

        Helper helper = new Helper();

        act = helper.getUserInput("enter E/e for Encryption or D/d for Decryption");

        isDir = helper.askUserToChooseDirOrFile("enter D/d for Directory or F/f for file");

        encryptionAlgorithmChosen = helper.ChooseEncryptionAlgorithm("enter D/d for Double, R/r for Reverse, S/s for Split, or O/o for other algorithms");
        if(!helper.isSimpleAlgorithmHasChosen())
            helper.getMoreAlgorithmsIfComplexAlgorithmChosen(encryptionAlgorithmChosen);

        keyPath = helper.getFilePathFromUser(myFiles, isDir,(isDir ?"please insert the path of the directory" :"please insert the path of the file") );


        if(act.equals("encrypt"))
            key.generateNewKey(keyPath);
        else {
            Scanner in = new Scanner(System.in);
            String pathOFKey;
            try {
                System.out.print("\nenter the path of the Key: ");
                pathOFKey=in.nextLine();
                key.getTheKeyFromPath(pathOFKey);
            }
            catch (IllegalKeyException ex) {
                System.out.println("\nyou need to insert valid path of the key");
            }
            System.out.println();
        }



        if(isDir) {
            long timeStarted = System.nanoTime();
            System.out.println("\nthe "+act+" of the directory in "+(isSync ? "Sync": "Async")+ " approach has started\n");

            isSync =  helper.askUserIfHeWantsSyncOrAsync("enter S/s for sync or A/a for async");
            if(isSync)
                helper.doSyncActionOnDir(myFiles,act,encryptionAlgorithmChosen,key);
            else if(!isSync)
                helper.doAsyncActionOnDir(myFiles,act,encryptionAlgorithmChosen,key);
            System.out.println("\nthe "+act+" of the directory in "+(isSync ? "Sync": "Async")+ " approach is done");
            System.out.println("the time it took(in seconds): "+ TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-timeStarted));
        }

        if(!isDir) {
           helper.doActionOnFile(myFiles, 0, act, encryptionAlgorithmChosen,key); //encrypt/decrypt the file
       }

    }
}
