package encryptor;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class App {
    public static void main( String[] args ) {
        ArrayList<MyFile> myFiles = new ArrayList<MyFile>();
        String act, encryptionAlgorithmChosen = null;
        int idx;
        boolean isDir;
        boolean isSync = false;
        boolean wantToChange = false;
        boolean wantToImportExport = false;
        String pathOfBaseDir;
        Key key;

        Helper helper = new Helper();

        try {
            encryptionAlgorithmChosen = helper.getDefaultAlgorithmFromXML();
        }catch (Exception ex){
            System.out.println("something wrong with JAXB");
            ex.printStackTrace();
        }


        act = helper.getUserInput("enter E/e for Encryption or D/d for Decryption");


        isDir = helper.askUserToChooseDirOrFile("enter D/d for Directory or F/f for file");

        pathOfBaseDir = helper.getFilePathFromUser(myFiles, isDir,(isDir ?"please insert the path of the directory" :"please insert the path of the file") );


        key = helper.generateOrGetKeyFromUser(act,pathOfBaseDir);

        wantToChange = helper.askUserIfHeWantToChangeDefaultAlgorithm("enter Y/y if you want to change the default Algorithm or N/n if you don't");

        if(wantToChange) {
            encryptionAlgorithmChosen = helper.ChooseEncryptionAlgorithm("enter D/d for Double, R/r for Reverse, S/s for Split, or O/o for other algorithms");
            if (!helper.isSimpleAlgorithmHasChosen())
                helper.getMoreAlgorithmsIfComplexAlgorithmChosen(encryptionAlgorithmChosen);
        }

        wantToImportExport = helper.askUserIfHeWantsToExportImportAlgorithmToXML("enter Y/y if you want to import/export the Algorithm to XML file or N/n if you don't");
        if(wantToImportExport){
            String importOrExport = helper.askUserToChooseImportOrExport("enter M/m for import or X/x for export");
            encryptionAlgorithmChosen = helper.importExportAlgorithmToXMLFile(importOrExport,encryptionAlgorithmChosen);
        }


        if(isDir) {
            long timeStarted = System.nanoTime();
            isSync =  helper.askUserIfHeWantsSyncOrAsync("enter S/s for sync or A/a for async");

            System.out.println("\nthe "+act+" of the directory in "+(isSync ? "Sync": "Async")+ " approach has started\n");

            if(isSync)
                helper.doSyncActionOnDir(myFiles,act,encryptionAlgorithmChosen,key);
            else if(!isSync)
                helper.doAsyncActionOnDir(myFiles,act,encryptionAlgorithmChosen,key);

            System.out.println("\nthe "+act+" of the directory in "+(isSync ? "Sync": "Async")+ " approach is done");

            System.out.println("the time it took(in seconds): "+ TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-timeStarted));
        }

        if(!isDir) {
            try {
                helper.doActionOnFile(myFiles, 0, act, encryptionAlgorithmChosen, key); //encrypt/decrypt the file
            }catch (Exception ex){
                System.out.println("exception name: "+ex.getClass().getSimpleName());
                System.out.println("exception message: "+ex.getMessage());

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                System.out.println("exception Stacktrace: "+ sw.toString());
            }
       }

    }
}
