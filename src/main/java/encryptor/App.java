package encryptor;


import java.util.ArrayList;


public class App {
    public static void main( String[] args ) {
        ArrayList<myFile> myFiles = new ArrayList<myFile>();
        ArrayList<String> myFilesPaths = new ArrayList<String>();
        String act, encryptionAlgorithmChosen;
        int idx;


        Helper helper = new Helper();
        act = helper.getUserInput("enter E/e for Encryption or D/d for Decryption");
        idx = helper.getFilePathFromUser(myFiles,myFilesPaths,"please insert the path of the file");
        encryptionAlgorithmChosen = helper.ChooseEncryptionAlgorithm("enter D/d for Double, R/r for Reverse, S/s for Split, or O/o for other algorithms");
        helper.doActionOnFile(myFiles,idx,act,encryptionAlgorithmChosen); //encrypt/decrypt the file

    }
}
