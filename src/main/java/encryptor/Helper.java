package encryptor;

import java.util.ArrayList;
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
                System.out.println("you have to choose whether to encrypt or decrypt.");

        }
        return act;
    }

    public int getFilePathFromUser(ArrayList<myFile> myFiles,ArrayList<String> myFilesPaths,String prompt) {
        boolean hasToChoosePath = true;
        int idx = -2;
        String path;

        Scanner in = new Scanner(System.in);

        while(hasToChoosePath){
            System.out.print(prompt + ": ");// we ask the user to insert the path of the file
            path = in.nextLine();

            File file = new File(path);
            if(file.exists() && !file.isDirectory() && file.canRead()) {
                myFile userFile = new myFile(file.getName(),path);
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
        String result = null;

        if(act.equals("encrypt")) {
            result = myFiles.get(idx).encryptFile();
        }
        else if(act.equals("decrypt")) {
            result = myFiles.get(idx).decryptFile();
        }

        System.out.println(result);
    }


}
