package encryptor;


import java.util.ArrayList;

public class App {
    public static void main( String[] args ) {
        ArrayList<myFile> myFiles = new ArrayList<myFile>();
        ArrayList<String> myFilesPaths = new ArrayList<String>();
        String act;
        int idx;


        Helper helper = new Helper();
        act = helper.getUserInput("enter E/e for Encryption or D/d for Decryption");
        idx = helper.getFilePathFromUser(myFiles,myFilesPaths,"please insert the path of the file");
        System.out.println("idx: " + idx + "  act: " + act);
        helper.doActionOnFile(myFiles,idx,act); //encrypt/decrypt the file


    }
}
