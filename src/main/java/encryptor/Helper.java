package encryptor;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by murad on 01/07/2016.
 */
public class Helper {

     private String firstAlgorithmChosen = null;
     private String secondAlgorithmChosen = null;

    @Getter private boolean simpleAlgorithmHasChosen = false;

    public String getUserInput(String prompt) {
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

    public boolean askUserIfHeWantsSyncOrAsync(String prompt){
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
        String keyPath = null;

        Scanner in = new Scanner(System.in);

        while(hasToChoosePath){
            System.out.print("\n"+prompt + ": ");// we ask the user to insert the path of the file
            path = in.nextLine();


            if(!isDir) {
                File file = new File(path);
                if (file.exists() && !file.isDirectory() && file.canRead()) {

                    MyFile userFile = new MyFile(path);
                    MyFiles.add(userFile);
                    keyPath = userFile.getFilePath();
                    hasToChoosePath = false;

                } else
                    System.out.println("you have to insert a path of existing readable file (not directory)\n");
            }
            else if(isDir){
                File Dir = new File(path);
                if(Dir.exists() && Dir.isDirectory()){
                    for(File fileEntry : Dir.listFiles()){
                        if(!fileEntry.isDirectory()){
                            MyFile fileInDir = new MyFile(fileEntry.getAbsolutePath());
                            MyFiles.add(fileInDir);
                        }
                    }
                    File encryptedDir = new File(path+"\\encrypted");
                    File decryptedDir = new File(path+"\\decrypted");
                    try{
                        if(!encryptedDir.exists())
                        encryptedDir.mkdir();
                        if(!decryptedDir.exists())
                        decryptedDir.mkdir();
                    }
                    catch (SecurityException ex){
                        System.out.println("we Can't make sub-directories");
                    }
                    keyPath = path;
                    hasToChoosePath = false;
                }else
                    System.out.println("you have to insert a path of existing directory\n");
            }

        }
        return keyPath;
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
                simpleAlgorithmHasChosen = false;
                hasToChooseEncryptionAlgorithm = false;
            } else if (input.equals("R") || input.equals("r")) {
                System.out.println("you chose Reverse Algorithm");
                encryptionAlgorithmChosen = "reverse";
                simpleAlgorithmHasChosen = false;
                hasToChooseEncryptionAlgorithm = false;
            } else if (input.equals("S") || input.equals("s")) {
                System.out.println("you chose Split Algorithm");
                encryptionAlgorithmChosen = "split";
                simpleAlgorithmHasChosen = false;
                hasToChooseEncryptionAlgorithm = false;
            }else if (input.equals("O") || input.equals("o")) {
                encryptionAlgorithmChosen = ChooseSimpleEncryptionAlgorithm("enter C/c for Caesar, X/x for XOR, or M/m for Multiplication");
                simpleAlgorithmHasChosen = true;
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




    public EncryptionAlgorithm getSimpleAlgorithmInstance(String encryptionAlgorithmChosen) {

        EncryptionAlgorithm simpleAlgorithmInstance;

        switch (encryptionAlgorithmChosen){
            case "caesar": simpleAlgorithmInstance = new CaesarAlgorithm();
                simpleAlgorithmHasChosen = true;
                break;
            case "xor": simpleAlgorithmInstance = new XorAlgorithm();
                simpleAlgorithmHasChosen = true;
                break;
            case "multi": simpleAlgorithmInstance = new MultiplicationAlgorithm();
                simpleAlgorithmHasChosen = true;
                break;
            default:
                simpleAlgorithmInstance = new CaesarAlgorithm();
                simpleAlgorithmHasChosen = false;
                break;
        }

        return simpleAlgorithmInstance;
    }




    public EncryptionAlgorithmsWithGeneric getComplexAlgorithmInstance(String encryptionAlgorithmChosen) {

        EncryptionAlgorithmsWithGeneric complexAlgorithmInstance;

        switch (encryptionAlgorithmChosen){
            case "double": complexAlgorithmInstance = new DoubleAlgorithm();
                simpleAlgorithmHasChosen = false;
                break;
            case "reverse": complexAlgorithmInstance = new ReverseAlgorithm();
                simpleAlgorithmHasChosen = false;
                break;
            case "split": complexAlgorithmInstance= new SplitAlgorithm();
                simpleAlgorithmHasChosen = false;
                break;
            default:
                complexAlgorithmInstance = new ReverseAlgorithm();
                simpleAlgorithmHasChosen = false;
                break;
        }

        return complexAlgorithmInstance;
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






    public void doActionOnFile(ArrayList<MyFile> MyFiles, int idx, String act, String encryptionAlgorithmChosen,Key key) {

        EncryptionAlgorithm simpleEncryptionAlgorithm = null;
        EncryptDecryptObservable firstAlgorithm = null, secondAlgorithm = null;
        EncryptionAlgorithmsWithGeneric complexEncryptionAlgorithm = null;

        simpleEncryptionAlgorithm = getSimpleAlgorithmInstance(encryptionAlgorithmChosen);

        if(!simpleAlgorithmHasChosen){
            complexEncryptionAlgorithm = getComplexAlgorithmInstance(encryptionAlgorithmChosen);
            firstAlgorithm = (EncryptDecryptObservable)getSimpleAlgorithmInstance(firstAlgorithmChosen);
            if(secondAlgorithmChosen != null)
                secondAlgorithm = (EncryptDecryptObservable)getSimpleAlgorithmInstance(secondAlgorithmChosen);
        }



        MyFile myfile = MyFiles.get(idx);
        if(act.equals("encrypt")) {

            if(simpleAlgorithmHasChosen)
                simpleEncryptionAlgorithm.encrypt(key.getKey()[0], myfile);
            else
                complexEncryptionAlgorithm.<EncryptDecryptObservable,EncryptDecryptObservable>encrypt(key,myfile,firstAlgorithm,secondAlgorithm);
        }

        else if(act.equals("decrypt")) {

            if(simpleAlgorithmHasChosen)
                simpleEncryptionAlgorithm.decrypt(key.getKey()[0], myfile);
            else
                complexEncryptionAlgorithm.<EncryptDecryptObservable,EncryptDecryptObservable>decrypt(key,myfile,firstAlgorithm,secondAlgorithm);
        }
    }


    public void doSyncActionOnDir(ArrayList<MyFile> myFiles,String act, String encryptionAlgorithmChosen, Key key){
        for(int i = 0;i< myFiles.size();i++)
            doActionOnFile(myFiles,i,act,encryptionAlgorithmChosen,key);
    }

    public void doAsyncActionOnDir(ArrayList<MyFile> myFiles,String act, String encryptionAlgorithmChosen, Key key){
        Executor executor = new threadPerTaskExecutor();
        int numOfFilesInDir = myFiles.size();

        final CountDownLatch done = new CountDownLatch(numOfFilesInDir);
        for(int i=0;i<numOfFilesInDir;i++) {
            final Integer idx = new Integer(i);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        doActionOnFile(myFiles,idx,act,encryptionAlgorithmChosen,key);

                    }finally {
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

    }


}
