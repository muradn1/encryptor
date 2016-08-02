package encryptor;

import EncryptionWithJAXB.jaxb.ObjectFactory;
import lombok.Getter;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


/**
 * Created by murad on 01/07/2016.
 */
public class Helper {

    @Getter private String firstAlgorithmChosen = null;
    @Getter private String secondAlgorithmChosen = null;

    @Getter public boolean isSimpleAlgorithmHasChosen = false;

    public String askUserToChooseEncryptOrDecrypt(String prompt) {
        String input,act=null;
        boolean hasToChooseAction = true;

        Scanner in = new Scanner(System.in);
        while (hasToChooseAction) {
            System.out.print(prompt + ": ");// we ask the user to choose the action he wants (encrypt\decrypt)
            input = in.nextLine();

            if (input.equals("E") || input.equals("e")) {
                System.out.println("you chose encryption");
                act = "encrypt";
                hasToChooseAction = false;
            } else if (input.equals("D") || input.equals("d")) {
                System.out.println("you chose decryption");
                act = "decrypt";
                hasToChooseAction = false;
            } else
                System.out.println("you have to choose whether to encrypt or decrypt.\n");

        }
        return act;
    }


    public boolean askUserToChooseDirOrFile(String prompt){
        boolean isDir = false;
        boolean hasToDecide = true;
        String input;

        Scanner in = new Scanner(System.in);
        while (hasToDecide) {
            System.out.print(prompt + ": ");// we ask the user to decide whether he wants to encrypt\decrypt file or directory
            input = in.nextLine();
            if (input.equals("D") || input.equals("d")) {
                System.out.println("you chose directory");
                isDir = true;
                hasToDecide = false;
            } else if (input.equals("F") || input.equals("f")) {
                System.out.println("you chose file");
                isDir = false;
                hasToDecide = false;
            } else
                System.out.println("you have to choose file or directory.\n");

        }

        return isDir;
    }

    public boolean askUserToChooseSyncOrAsync(String prompt){
        boolean isSync = false;
        boolean hasToChooseSyncOrAsync = true;
        String input;

        Scanner in = new Scanner(System.in);
        while (hasToChooseSyncOrAsync) {
            System.out.print(prompt + ": ");// we ask the user to decide whether he wants to use sync or async approach
            input = in.nextLine();
            if (input.equals("S") || input.equals("s")) {
                System.out.println("you chose sync");
                isSync = true;
                hasToChooseSyncOrAsync = false;
            } else if (input.equals("A") || input.equals("a")) {
                System.out.println("you chose async");
                isSync = false;
                hasToChooseSyncOrAsync = false;
            } else
                System.out.println("you have to choose sync or async.\n");
        }
        return isSync;
    }


    public String getFilePathFromUser(ArrayList<MyFile> MyFiles,boolean isDir, String prompt) {
        boolean hasToChoosePath = true;
        String path;
        String pathOfBaseDir = null;

        Scanner in = new Scanner(System.in);

        while(hasToChoosePath){
            System.out.print("\n"+prompt + ": ");// we ask the user to insert the path of the file
            path = in.nextLine();

            if(!isDir) {
                File file = new File(path);
                if (file.exists() && !file.isDirectory() && file.canRead()) {

                    MyFile userFile = new MyFile(path);
                    MyFiles.add(userFile);
                    pathOfBaseDir = userFile.getFilePath();
                    hasToChoosePath = false;

                } else
                    System.out.println("you have to insert a path of existing readable file (not directory)\n");
            }
            else if(isDir){
                File Dir = new File(path);
                if(Dir.exists() && Dir.isDirectory()){

                    pathOfBaseDir = path;
                    hasToChoosePath = false;

                }else
                    System.out.println("you have to insert a path of existing directory\n");
            }
        }
        return pathOfBaseDir;
    }

    public ArrayList<MyFile> getFilesFromDirPath(String pathOfBaseDir, String act){
        ArrayList<MyFile> myFiles = new ArrayList<MyFile>();

        File Dir = new File(pathOfBaseDir);
        for(File fileEntry : Dir.listFiles()){
            if(!fileEntry.isDirectory()){
                MyFile fileInDir = new MyFile(fileEntry.getAbsolutePath());
                myFiles.add(fileInDir);
            }
        }
        if(act.equals("encrypt")){
            File encryptedDir = new File(pathOfBaseDir+"\\encrypted");
            try{
                if(!encryptedDir.exists())
                    encryptedDir.mkdir();
            }catch (SecurityException ex){
                System.out.println("we Can't make encrypted sub-directory");
                ex.printStackTrace();
            }
        }
        else if(act.equals("decrypt")){
            File decryptedDir = new File(pathOfBaseDir+"\\decrypted");
            try{
                if(!decryptedDir.exists())
                    decryptedDir.mkdir();
             }catch (SecurityException ex){
                System.out.println("we Can't make sub-directories");
                ex.printStackTrace();
            }
        }

        return myFiles;
    }


    public String ChooseEncryptionAlgorithm(String prompt) {
        String input,encryptionAlgorithmChosen = null;
        Scanner in = new Scanner(System.in);
        boolean hasToChooseEncryptionAlgorithm = true;

        while(hasToChooseEncryptionAlgorithm) {
            System.out.print("\n"+prompt + ": ");// we ask the user to choose the algorithm he wants to use
            input = in.nextLine();

            if (input.equals("D") || input.equals("d")) {
                System.out.println("you chose Double Algorithm");
                encryptionAlgorithmChosen = "double";
                isSimpleAlgorithmHasChosen = false;
                hasToChooseEncryptionAlgorithm = false;
            } else if (input.equals("R") || input.equals("r")) {
                System.out.println("you chose Reverse Algorithm");
                encryptionAlgorithmChosen = "reverse";
                isSimpleAlgorithmHasChosen = false;
                hasToChooseEncryptionAlgorithm = false;
            } else if (input.equals("S") || input.equals("s")) {
                System.out.println("you chose Split Algorithm");
                encryptionAlgorithmChosen = "split";
                isSimpleAlgorithmHasChosen = false;
                hasToChooseEncryptionAlgorithm = false;
            }else if (input.equals("O") || input.equals("o")) {
                encryptionAlgorithmChosen = ChooseSimpleEncryptionAlgorithm("enter C/c for Caesar, X/x for XOR, or M/m for Multiplication");
                isSimpleAlgorithmHasChosen = true;
                hasToChooseEncryptionAlgorithm = false;
            }else
                System.out.println("you have to choose one of the Algorithms proposed.\n");

        }

        return encryptionAlgorithmChosen;

    }




    public String ChooseSimpleEncryptionAlgorithm(String prompt) {
        String input,simpleEncryptionAlgorithmChosen = null;
        Scanner in = new Scanner(System.in);
        boolean hasToChooseEncryptionAlgorithm = true;

        while(hasToChooseEncryptionAlgorithm) {
            System.out.print(prompt + ": ");// we ask the user to choose the algorithm he wants to use
            input = in.nextLine();

            if (input.equals("C") || input.equals("c")) {
                System.out.println("you chose Caesar Algorithm");
                simpleEncryptionAlgorithmChosen = "caesar";
                hasToChooseEncryptionAlgorithm = false;
            } else if (input.equals("X") || input.equals("x")) {
                System.out.println("you chose XOR Algorithm");
                simpleEncryptionAlgorithmChosen = "xor";
                hasToChooseEncryptionAlgorithm = false;
            } else if (input.equals("M") || input.equals("m")) {
                System.out.println("you chose Multiplication Algorithm");
                simpleEncryptionAlgorithmChosen = "multi";
                hasToChooseEncryptionAlgorithm = false;
            }else
                System.out.println("you have to choose one of the Algorithms proposed.\n");

        }

        return simpleEncryptionAlgorithmChosen;

    }



    public void getMoreAlgorithmsIfComplexAlgorithmChosen(String encryptionAlgorithmChosen) {
        System.out.println("\nyou chose complex algorithm, for that you need to choose 1 more algorithms to be used in");
        System.out.println("Choose the first algorithm:");
        firstAlgorithmChosen = ChooseSimpleEncryptionAlgorithm("enter C/c for Caesar, X/x for XOR, or M/m for Multiplication");

        if(encryptionAlgorithmChosen.equals("double")) {
            System.out.println("\nyou chose very complex algorithm, for that you need to choose 1 more algorithms to be used in");
            System.out.println("Choose the second algorithm:");
            secondAlgorithmChosen = ChooseSimpleEncryptionAlgorithm("enter C/c for Caesar, X/x for XOR, or M/m for Multiplication");
        }
    }






    public void doActionOnFile(ArrayList<MyFile> MyFiles, int idx, String act, EncryptionAlgorithms encryptionAlgorithms,Key key) throws Exception {

        EncryptionAlgorithm simpleEncryptionAlgorithm = null;
        EncryptDecryptObservable firstAlgorithm = null, secondAlgorithm = null;
        EncryptionAlgorithmsWithGeneric complexEncryptionAlgorithm = null;


        if(!isSimpleAlgorithmHasChosen){
            complexEncryptionAlgorithm = encryptionAlgorithms.getComplexMainEncryptionAlgorithmInstance();
            firstAlgorithm = (EncryptDecryptObservable)encryptionAlgorithms.getFirstSubAlgorithmInstance();
            if(secondAlgorithmChosen != null)
                secondAlgorithm = (EncryptDecryptObservable)encryptionAlgorithms.getSecondSubAlgorithmInstance();
        }else
            simpleEncryptionAlgorithm = encryptionAlgorithms.getSimpleMainEncryptionAlgorithmInstance();

        if(simpleEncryptionAlgorithm == null && complexEncryptionAlgorithm == null) {
            throw new NullPointerException();
        }


        MyFile myfile = MyFiles.get(idx);
        if(act.equals("encrypt")) {

            if(isSimpleAlgorithmHasChosen)
                simpleEncryptionAlgorithm.encrypt(key.getKey()[0], myfile);
            else
                complexEncryptionAlgorithm.<EncryptDecryptObservable,EncryptDecryptObservable>encrypt(key,myfile,firstAlgorithm,secondAlgorithm);
        }

        else if(act.equals("decrypt")) {

            if(isSimpleAlgorithmHasChosen)
                simpleEncryptionAlgorithm.decrypt(key.getKey()[0], myfile);
            else
                complexEncryptionAlgorithm.<EncryptDecryptObservable,EncryptDecryptObservable>decrypt(key,myfile,firstAlgorithm,secondAlgorithm);
        }
    }


    public void doSyncActionOnDir(ArrayList<MyFile> myFiles,String act, EncryptionAlgorithms encryptionAlgorithms, Key key){

        String pathOFDir = myFiles.get(0).getFilePath();
        reportwithJAXB.jaxb.ObjectFactory objectFactory = new reportwithJAXB.jaxb.ObjectFactory();
        reportwithJAXB.jaxb.ReportForDirectory reportForDirectory = objectFactory.createReportForDirectory();
        reportForDirectory.setNameOfDirectory(pathOFDir);//the name of the directory is the path to any file in it
        reportwithJAXB.jaxb.ReportForDirectory.FilesInDirectory filesInDirectory = objectFactory.createReportForDirectoryFilesInDirectory();
        reportForDirectory.setFilesInDirectory(filesInDirectory);
        filesInDirectory.setFiles(new ArrayList<reportwithJAXB.jaxb.ReportForDirectory.FilesInDirectory.File>());
        long startedTime,endTime;
        for(int i = 0;i< myFiles.size();i++){
            reportwithJAXB.jaxb.ReportForDirectory.FilesInDirectory.File file = objectFactory.createReportForDirectoryFilesInDirectoryFile();
            file.setNameOfFile(myFiles.get(i).getFileName()+"."+myFiles.get(i).getExtension());
            file.setAction(act);
            try{
                startedTime = System.nanoTime();
                App.logger.info("the "+ act + " of the file:"+myFiles.get(i).getFileName() + " has started");
                doActionOnFile(myFiles,i,act,encryptionAlgorithms,key);
                endTime = System.nanoTime()-startedTime;
                file.setStatus("succeeded");
                reportwithJAXB.jaxb.ReportForDirectory.FilesInDirectory.File.IfSucceeded ifSucceeded = objectFactory.createReportForDirectoryFilesInDirectoryFileIfSucceeded();
                ifSucceeded.setTimeInMillis(TimeUnit.NANOSECONDS.toMillis(endTime));
                file.setIfSucceeded(ifSucceeded);
                App.logger.info("the "+ act + " of the file:"+myFiles.get(i).getFileName() + " is done");
                App.logger.info("the time it took(in seconds) for the file - " +myFiles.get(i).getFileName() + "is: "+ TimeUnit.NANOSECONDS.toSeconds(endTime));

            }catch (Exception ex){
                file.setStatus("failed");
                reportwithJAXB.jaxb.ReportForDirectory.FilesInDirectory.File.IfFailed ifFailed = objectFactory.createReportForDirectoryFilesInDirectoryFileIfFailed();
                ifFailed.setExceptionName(ex.getClass().getSimpleName());
                ifFailed.setExceptionMessage(ex.getMessage());
                StringWriter sw = new StringWriter();//
                PrintWriter pw = new PrintWriter(sw);// to get Stacktrace as String
                ex.printStackTrace(pw);//
                ifFailed.setExceptionStacktrace(sw.toString());
                file.setIfFailed(ifFailed);

                App.logger.info("the "+ act + " of the file: "+myFiles.get(i).getFileName() + " hasStopped");
                App.logger.error("error in "+act+" the file "+myFiles.get(i).getFileName()+" because of the exception: " + ex.getClass().getSimpleName() );

            }finally {
                filesInDirectory.getFiles().add(file);
            }
        }
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance("reportwithJAXB.jaxb");
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
            marshaller.marshal(reportForDirectory,new FileOutputStream(pathOFDir+"\\report.xml"));
        }catch (Exception ex){
            System.out.println("something wrong generating report in sync");
            ex.printStackTrace();
        }


    }

    public void doAsyncActionOnDir(ArrayList<MyFile> myFiles,String act, EncryptionAlgorithms encryptionAlgorithms, Key key){
        Executor executor = new threadPerTaskExecutor();
        int numOfFilesInDir = myFiles.size();
        String pathOFDir = myFiles.get(0).getFilePath();
        reportwithJAXB.jaxb.ObjectFactory objectFactory = new reportwithJAXB.jaxb.ObjectFactory();
        reportwithJAXB.jaxb.ReportForDirectory reportForDirectory = objectFactory.createReportForDirectory();
        reportForDirectory.setNameOfDirectory(pathOFDir);//the name of the directory is the path to any file in it
        reportwithJAXB.jaxb.ReportForDirectory.FilesInDirectory filesInDirectory = objectFactory.createReportForDirectoryFilesInDirectory();
        reportForDirectory.setFilesInDirectory(filesInDirectory);
        filesInDirectory.setFiles(new ArrayList<reportwithJAXB.jaxb.ReportForDirectory.FilesInDirectory.File>());


        final CountDownLatch done = new CountDownLatch(numOfFilesInDir);
        for(int i=0;i<numOfFilesInDir;i++) {
            final Integer idx = new Integer(i);
            reportwithJAXB.jaxb.ReportForDirectory.FilesInDirectory.File file = objectFactory.createReportForDirectoryFilesInDirectoryFile();
            file.setNameOfFile(myFiles.get(i).getFileName()+"."+myFiles.get(i).getExtension());
            file.setAction(act);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                       final long startedTime = System.nanoTime();
                        App.logger.info("the "+ act + " of the file:"+myFiles.get(idx).getFileName() + " has started");
                        doActionOnFile(myFiles,idx,act,encryptionAlgorithms,key);
                        final long endTime = System.nanoTime()-startedTime;
                        file.setStatus("succeeded");
                        reportwithJAXB.jaxb.ReportForDirectory.FilesInDirectory.File.IfSucceeded ifSucceeded = objectFactory.createReportForDirectoryFilesInDirectoryFileIfSucceeded();
                        ifSucceeded.setTimeInMillis(TimeUnit.NANOSECONDS.toMillis(endTime));
                        file.setIfSucceeded(ifSucceeded);
                        App.logger.info("the "+ act + " of the file: "+myFiles.get(idx).getFileName() + " is done");
                        App.logger.info("the time it took(in seconds) for the file - "+myFiles.get(idx).getFileName()+ "is: " + TimeUnit.NANOSECONDS.toSeconds(endTime));

                    }catch (Exception ex){
                        file.setStatus("failed");
                        reportwithJAXB.jaxb.ReportForDirectory.FilesInDirectory.File.IfFailed ifFailed = objectFactory.createReportForDirectoryFilesInDirectoryFileIfFailed();
                        ifFailed.setExceptionName(ex.getClass().getSimpleName());
                        ifFailed.setExceptionMessage(ex.getMessage());
                        StringWriter sw = new StringWriter();//
                        PrintWriter pw = new PrintWriter(sw);// to get Stacktrace as String
                        ex.printStackTrace(pw);//
                        ifFailed.setExceptionStacktrace(sw.toString());
                        file.setIfFailed(ifFailed);

                        App.logger.info("the "+ act + " of the file: "+myFiles.get(idx).getFileName() + " hasStopped");
                        App.logger.error("error in "+act+" the file "+myFiles.get(idx).getFileName()+" because of the exception: " + ex.getClass().getSimpleName() );

                    }
                    finally {
                        filesInDirectory.getFiles().add(file);
                        done.countDown();
                    }

                }
            });
        }
        try {
            done.await();
        }catch (InterruptedException ex) {
            System.out.println("the waiting has interrupted in helper.doAsyncActionOnDir");
            ex.printStackTrace();
        }

        try{
            JAXBContext jaxbContext = JAXBContext.newInstance("reportwithJAXB.jaxb");
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
            marshaller.marshal(reportForDirectory,new FileOutputStream(pathOFDir+"\\report.xml"));
        }catch (Exception ex){
            System.out.println("something wrong generating report in async");
            ex.printStackTrace();
        }

    }

    public boolean isSimpleAlgorithmHasChosen(String mainEncryptionAlgorithmChosen){
        switch (mainEncryptionAlgorithmChosen){
            case "caesar":
                isSimpleAlgorithmHasChosen = true;
                break;
            case "xor":
                isSimpleAlgorithmHasChosen = true;
                break;
            case "multi":
                isSimpleAlgorithmHasChosen = true;
                break;
            case "double":
                isSimpleAlgorithmHasChosen = false;
                break;
            case "reverse":
                isSimpleAlgorithmHasChosen = false;
                break;
            case "split":
                isSimpleAlgorithmHasChosen = false;
                break;
            default:
                isSimpleAlgorithmHasChosen = true;
                break;
        }
        return isSimpleAlgorithmHasChosen;
    }


    public String getDefaultAlgorithmFromXML() throws Exception{

        String encryptionAlgorithm;
        System.out.printf("the default algorithms are:\n");
        encryptionAlgorithm = importAlgorithmFromXMLFile("defaultEncryptionAlgorithm.xml");
        return encryptionAlgorithm;

    }


    public String importAlgorithmFromXMLFile(String pathToXMLFile) throws JAXBException,SAXException{

        JAXBContext jc = JAXBContext.newInstance("EncryptionWithJAXB.jaxb");
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema = factory.newSchema(new File("defaultEncryptionAlgorithm.xsd"));
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setSchema(schema);

        EncryptionWithJAXB.jaxb.DefaulEencryptionAlgorithm defaulEencryptionAlgorithm =
                (EncryptionWithJAXB.jaxb.DefaulEencryptionAlgorithm) unmarshaller.unmarshal(new File(pathToXMLFile));
        System.out.printf("the main algorithm is: %s\n the subAlgorithm1 is: %s\n the subAlgorithm2 is: %s\n\n",defaulEencryptionAlgorithm.getMainAlgorithm(),defaulEencryptionAlgorithm.getSubAlgoruthm1(),defaulEencryptionAlgorithm.getSubAlgoruthm2());
        this.firstAlgorithmChosen = defaulEencryptionAlgorithm.getSubAlgoruthm1();
        this.secondAlgorithmChosen = defaulEencryptionAlgorithm.getSubAlgoruthm2();
        return defaulEencryptionAlgorithm.getMainAlgorithm();
    }

    public boolean askUserIfHeWantToChangeDefaultAlgorithm(String prompt) {
        String input;
        boolean wantToChange = false;
        boolean hasToDecideWhetherToChangeOrNot = true;

        Scanner in = new Scanner(System.in);
        while (hasToDecideWhetherToChangeOrNot) {
            System.out.print(prompt + ": ");// we ask the user if he wants to change the default algorithm (y\n)
            input = in.nextLine();

            if (input.equals("Y") || input.equals("y")) {
                System.out.println("you want to change the default algorithm");
                wantToChange = true;
                hasToDecideWhetherToChangeOrNot = false;
            } else if (input.equals("N") || input.equals("n")) {
                System.out.println("you don't want to change the default algorithm");
                wantToChange = false;
                hasToDecideWhetherToChangeOrNot = false;
            } else
                System.out.println("you have to choose whether to change the default or not.\n");

        }
        return wantToChange;
    }

    public Key generateOrGetKeyFromUser(String act, String keyPath){
        Key key = new Key();
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

        return key;
    }

    public boolean askUserIfHeWantsToExportImportAlgorithmToXML(String prompt){
        String input;
        boolean wantToExportImport = false;
        boolean hasToDecideIfToImportExport = true;

        Scanner in = new Scanner(System.in);
        while (hasToDecideIfToImportExport) {
            System.out.print(prompt + ": ");// we ask the user if he wants to export/import the algorithm to XML(y\n)
            input = in.nextLine();

            if (input.equals("Y") || input.equals("y")) {
                System.out.println("you want to Import/Export");
                wantToExportImport = true;
                hasToDecideIfToImportExport = false;
            } else if (input.equals("N") || input.equals("n")) {
                System.out.println("you don't want to Import/Export");
                wantToExportImport = false;
                hasToDecideIfToImportExport = false;
            } else
                System.out.println("you have to choose whether you want to Import/Export or not.\n");

        }
        return wantToExportImport;

    }

    public String askUserToChooseImportOrExport(String prompt){
        String input;
        String importOrExport = null;
        boolean hasToDecideIfToImportExport = true;

        Scanner in = new Scanner(System.in);
        while (hasToDecideIfToImportExport) {
            System.out.print(prompt + ": ");// we ask the user to choose import or export
            input = in.nextLine();

            if (input.equals("M") || input.equals("m")) {
                System.out.println("you want to Import");
                importOrExport = "import";
                hasToDecideIfToImportExport = false;
            } else if (input.equals("X") || input.equals("x")) {
                System.out.println("you want to Export");
                importOrExport = "export";
                hasToDecideIfToImportExport = false;
            } else
                System.out.println("you have to choose whether you want to iMport or eXport.\n");

        }
        return importOrExport;
    }


    public String getPathForExport(String prompt){
        String path;
        Scanner in = new Scanner(System.in);
            System.out.print(prompt + ": ");// we ask the user to choose import or export
        path = in.nextLine();

        return path;

    }

    public String askUserForFilePath(String prompt){
        boolean hasToChoosePath = true;
        String path = null;

        Scanner in = new Scanner(System.in);

        while(hasToChoosePath){
            System.out.print("\n"+prompt + ": ");// we ask the user to insert the path of the file
            path = in.nextLine();

            File file = new File(path);
            if (file.exists() && !file.isDirectory()) {
                hasToChoosePath = false;

                } else
                    System.out.println("you have to insert a path of existing file (not directory)\n");
        }
        return path;
    }

    public void exportAlgorithmToXML(String pathToDirOfXML, String encryptionAlgorithm) throws JAXBException{

        EncryptionWithJAXB.jaxb.ObjectFactory objectFactory = new ObjectFactory();
        EncryptionWithJAXB.jaxb.DefaulEencryptionAlgorithm defaultEncryptionAlgorithm = objectFactory.createDefaulEencryptionAlgorithm();
        defaultEncryptionAlgorithm.setMainAlgorithm(encryptionAlgorithm);
        defaultEncryptionAlgorithm.setSubAlgoruthm1(this.firstAlgorithmChosen);
        defaultEncryptionAlgorithm.setSubAlgoruthm2(this.secondAlgorithmChosen);

        JAXBContext jaxbContext = JAXBContext.newInstance("EncryptionWithJAXB.jaxb");
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
        try{
        marshaller.marshal(defaultEncryptionAlgorithm,new FileOutputStream(pathToDirOfXML+"\\exportedAlgorithm.xml"));
        }catch (FileNotFoundException ex){
            System.out.println("something wrong with exporting xml file (with IO in helper.exportAlgorithmToXML method)");
            ex.printStackTrace();
        }



    }


    public String importExportAlgorithmToXMLFile(String importOrExport, String encryptionAlgorithmChosen){
        String encryptionAlgorithm = encryptionAlgorithmChosen;
        if(importOrExport.equals("import")){
            String pathToXML = askUserForFilePath("insert the path to the XML file You Want to import");
            try {
                encryptionAlgorithm = importAlgorithmFromXMLFile(pathToXML);
            }catch (Exception ex){
                System.out.println("something Wrong in importing XML file");
                ex.printStackTrace();
            }


        }
        else if(importOrExport.equals("export")){
            String pathToDirOfXML = getPathForExport("insert the path to the Directory of the XML you want to export to");
            try {
                exportAlgorithmToXML(pathToDirOfXML,encryptionAlgorithm);
            }catch (Exception ex){
                System.out.println("something Wrong in exporting XML file");
                ex.printStackTrace();
            }
        }

        return encryptionAlgorithm;
    }
    public void updateLog4jConfiguration(String logFile) {
        Enumeration appenders = App.logger.getAllAppenders();
        FileAppender fa = null;

        while(appenders.hasMoreElements()) {
            Appender currAppender = (Appender) appenders.nextElement();
            if(currAppender instanceof FileAppender) {
                fa = (FileAppender) currAppender;
            }
        }
        if(fa != null) {
            fa.setFile(logFile);
            fa.activateOptions();
        }
        else {
            System.out.println("No File Appender found");
        }

    }


}
