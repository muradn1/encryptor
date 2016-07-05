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

    public void doActionOnFile(ArrayList<myFile> myFiles,int idx, String act) {

        CaesarAlgorithm caesar = new CaesarAlgorithm();
        myFile myfile = myFiles.get(idx);

        if(act.equals("encrypt")) {
            Random random = new Random();
            int rand = random.nextInt(Byte.MAX_VALUE -1)+1;
            System.out.println("\nYour Key is: " + rand);
            caesar.encrypt((byte)rand, myfile);


        }
        else if(act.equals("decrypt")) {
            Scanner in = new Scanner(System.in);
            boolean hasToInsertKey = true;
            int key = 0;
            while(hasToInsertKey) {
                System.out.print("\nenter your Key: ");
                if(in.hasNextInt()) {
                    key = in.nextInt();
                    hasToInsertKey = false;
                }
                else
                    System.out.println("\nyou need to insert integer number for the KEY");
            }
            System.out.println();
            caesar.decrypt((byte)key,myfile);
        }

       // System.out.println(result);
    }


}
