package encryptor;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * Created by murad on 01/07/2016.
 */
public class Helper {

     private String firstAlgorithmChosen = null;
     private String secondAlgorithmChosen = null;
     private boolean simpleAlgorithmHasChosen = false;

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

    public int getFilePathFromUser(ArrayList<myFile> myFiles,ArrayList<String> myFilesPaths,String prompt) {
        boolean hasToChoosePath = true;
        int idx = -2;
        String path;

        Scanner in = new Scanner(System.in);

        while(hasToChoosePath){
            System.out.print("\n"+prompt + ": ");// we ask the user to insert the path of the file
            path = in.nextLine();

            File file = new File(path);
            if(file.exists() && !file.isDirectory() && file.canRead()) {
                myFile userFile = new myFile(path);
                idx = myFilesPaths.indexOf(path);
                if(idx<0) {//the file doesn't exist in the arrayList
                    myFilesPaths.add(path);
                    myFiles.add(userFile);
                    idx = myFilesPaths.indexOf(path);
                }
                System.out.println();
                hasToChoosePath = false;
            }
            else
                System.out.println("you have to insert a path of existing readable file (not directory)\n");

        }
        return idx;
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
                hasToChooseEncryptionAlgorithm = false;
            } else if (input.equals("R") || input.equals("r")) {
                System.out.println("you chose Reverse Algorithm");
                encryptionAlgorithmChosen = "reverse";
                hasToChooseEncryptionAlgorithm = false;
            } else if (input.equals("S") || input.equals("s")) {
                System.out.println("you chose Split Algorithm");
                encryptionAlgorithmChosen = "split";
                hasToChooseEncryptionAlgorithm = false;
            }else if (input.equals("O") || input.equals("o")) {
                encryptionAlgorithmChosen = ChooseSimpleEncryptionAlgorithm("enter C/c for Caesar, X/x for XOR, or M/m for Multiplication");
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

    public void doActionOnFile(ArrayList<myFile> myFiles,int idx, String act,String encryptionAlgorithmChosen) {

        EncryptionAlgorithm simpleEncryptionAlgorithm = null;
        EncryptDecryptObservable firstAlgorithm = null, secondAlgorithm = null;
        EncryptionAlgorithmsWithGeneric complexEncryptionAlgorithm = null;

        simpleEncryptionAlgorithm = getSimpleAlgorithmInstance(encryptionAlgorithmChosen);

        if(!simpleAlgorithmHasChosen){
            complexEncryptionAlgorithm = getComplexAlgorithmInstance(encryptionAlgorithmChosen);
            getMoreAlgorithmsIfComplexAlgorithmChosen(encryptionAlgorithmChosen);
            firstAlgorithm = (EncryptDecryptObservable)getSimpleAlgorithmInstance(firstAlgorithmChosen);
            if(secondAlgorithmChosen != null)
                secondAlgorithm = (EncryptDecryptObservable)getSimpleAlgorithmInstance(secondAlgorithmChosen);
        }



        myFile myfile = myFiles.get(idx);
        Key key = new Key();

        if(act.equals("encrypt")) {

            key.generateNewKey(myfile.getFilePath());
            if(simpleAlgorithmHasChosen)
                simpleEncryptionAlgorithm.encrypt(key.getKey()[0], myfile);
            else
                complexEncryptionAlgorithm.<EncryptDecryptObservable,EncryptDecryptObservable>encrypt(key,myfile,firstAlgorithm,secondAlgorithm);
        }

        else if(act.equals("decrypt")) {
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

            if(simpleAlgorithmHasChosen)
                simpleEncryptionAlgorithm.decrypt(key.getKey()[0], myfile);
            else
                complexEncryptionAlgorithm.<EncryptDecryptObservable,EncryptDecryptObservable>decrypt(key,myfile,firstAlgorithm,secondAlgorithm);
        }
    }


}
