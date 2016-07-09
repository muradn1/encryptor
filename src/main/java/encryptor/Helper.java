package encryptor;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
import java.io.IOException;

/**
 * Created by murad on 01/07/2016.
 */
public class Helper {
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

                hasToChoosePath = false;
            }
            else
                System.out.println("you have to insert a path of existing readable file (not directory)");

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

            if (input.equals("C") || input.equals("c")) {
                System.out.println("you chose Caesar Algorithm");
                encryptionAlgorithmChosen = "caesar";
                hasToChooseEncryptionAlgorithm = false;
            } else if (input.equals("X") || input.equals("x")) {
                System.out.println("you chose XOR Algorithm");
                encryptionAlgorithmChosen = "xor";
                hasToChooseEncryptionAlgorithm = false;
            } else if (input.equals("M") || input.equals("m")) {
                System.out.println("you chose Multiplication Algorithm");
                encryptionAlgorithmChosen = "multi";
                hasToChooseEncryptionAlgorithm = false;
            }else
                System.out.println("you have to choose one of the Algorithms proposed.\n");

        }

        return encryptionAlgorithmChosen;

    }

    public void doActionOnFile(ArrayList<myFile> myFiles,int idx, String act,String encryptionAlgorithmChosen) {

        //CaesarAlgorithm caesar = new CaesarAlgorithm();
        EncryptionAlgorithm encryptionAlgorithm;
        switch (encryptionAlgorithmChosen){
            case "caesar": encryptionAlgorithm = new CaesarAlgorithm();
                break;
            case "xor": encryptionAlgorithm = new XorAlgorithm();
                break;
            case "multi": encryptionAlgorithm = new MultiplicationAlgorithm();
                break;
            default: encryptionAlgorithm = new CaesarAlgorithm();
                break;
        }
        myFile myfile = myFiles.get(idx);

        if(act.equals("encrypt")) {

            Random random;
            int rand;
            do {
                random = new Random();
                rand = random.nextInt(Byte.MAX_VALUE -1)+1;
            }while (encryptionAlgorithmChosen.equals("multi") && (rand%2==0));
            System.out.println("\nYour Key is: " + rand);
            encryptionAlgorithm.encrypt((byte)rand, myfile);


        }
        else if(act.equals("decrypt")) {
            Scanner in = new Scanner(System.in);

            int key = 0;
            try {
                System.out.print("\nenter your Key: ");
                if (!in.hasNextInt() || (key=in.nextInt())>Byte.MAX_VALUE) {
                    throw  new IllegalKeyException();
                }
            }
            catch (IllegalKeyException ex) {
                System.out.println("\nyou need to insert integer number for the KEY that is less than "+(Byte.MAX_VALUE+1));

            }

            System.out.println();
            encryptionAlgorithm.decrypt((byte)key,myfile);
        }

       // System.out.println(result);
    }


}
