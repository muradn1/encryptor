package encryptor;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.log4j.*;


public class App {

    final static Logger logger = Logger.getRootLogger();

    public static void main( String[] args ) {
        ArrayList<MyFile> myFiles = new ArrayList<MyFile>();
        String act, encryptionAlgorithmChosen = null, pathOfBaseDir;
        int idx;
        boolean isDir,isSync = false, wantToChange = false, wantToImportExport = false, isSimpleAlgorithm;
        Key key;

        Helper helper = new Helper();

        try {// try to load default encryption algorithm from XML file
            encryptionAlgorithmChosen = helper.getDefaultAlgorithmFromXML();
        }catch (Exception ex){
            System.out.println("something wrong with JAXB");
            ex.printStackTrace();
        }


        act = helper.askUserToChooseEncryptOrDecrypt("enter E/e for Encryption or D/d for Decryption");


        isDir = helper.askUserToChooseDirOrFile("enter D/d for Directory or F/f for file");

        pathOfBaseDir = helper.getFilePathFromUser(myFiles, isDir,(isDir ?"please insert the path of the directory" :"please insert the path of the file") );

        if(isDir)
            myFiles = helper.getFilesFromDirPath(pathOfBaseDir,act);

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

        PropertyConfigurator.configure("log4j.properties");
        helper.updateLog4jConfiguration(pathOfBaseDir+"\\log.log");

        EncryptionAlgorithms encryptionAlgorithms;
        isSimpleAlgorithm = helper.isSimpleAlgorithmHasChosen(encryptionAlgorithmChosen);
        Injector injector;
        if(isSimpleAlgorithm) {
            //encryptionAlgorithms = new EncryptionAlgorithms(encryptionAlgorithmChosen);
            injector = Guice.createInjector(new AlgorithmsModule(encryptionAlgorithmChosen));
        }
        else {
            //encryptionAlgorithms = new EncryptionAlgorithms(encryptionAlgorithmChosen, helper.getFirstAlgorithmChosen(), helper.getSecondAlgorithmChosen());
            injector = Guice.createInjector(new AlgorithmsModule(encryptionAlgorithmChosen, helper.getFirstAlgorithmChosen(), helper.getSecondAlgorithmChosen()));
        }

        encryptionAlgorithms = injector.getInstance(EncryptionAlgorithms.class);

        if(isDir) {
            long timeStarted = System.nanoTime();
            isSync =  helper.askUserToChooseSyncOrAsync("enter S/s for sync or A/a for async");

            System.out.println("\nthe "+act+" of the directory in "+(isSync ? "Sync": "Async")+ " approach has started\n");

            if(isSync)
                helper.doSyncActionOnDir(myFiles,act,encryptionAlgorithms,key);
            else if(!isSync)
                helper.doAsyncActionOnDir(myFiles,act,encryptionAlgorithms,key);

            System.out.println("\nthe "+act+" of the directory in "+(isSync ? "Sync": "Async")+ " approach is done");

            System.out.println("the time it took(in seconds): "+ TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-timeStarted));
        }

        if(!isDir) {
            try {
                long startedTime = System.nanoTime();
                logger.info("the "+ act + " of the file:"+myFiles.get(0).getFileName() + " has started");
                helper.doActionOnFile(myFiles, 0, act, encryptionAlgorithms, key); //encrypt/decrypt the file
                logger.info("the "+ act + " of the file:"+myFiles.get(0).getFileName() + " is done");
                logger.info("the time it took(in seconds): "+ TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-startedTime));
            }catch (Exception ex){
                System.out.println("exception name: "+ex.getClass().getSimpleName());
                System.out.println("exception message: "+ex.getMessage());

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                System.out.println("exception Stacktrace: "+ sw.toString());
                logger.info("the "+ act + " of the file: "+myFiles.get(0).getFileName() + " hasStopped");
                logger.error("error in "+act+" the file "+myFiles.get(0).getFileName()+" because of the exception: " + ex.getClass().getSimpleName() );
            }
       }

    }
}
