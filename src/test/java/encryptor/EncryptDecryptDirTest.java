package encryptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by murad on 15/07/2016.
 */
public class EncryptDecryptDirTest {


    ByteArrayOutputStream baos;
    PrintStream outBuff;
    PrintStream oldOut;
    boolean alreadyExisted;
    int numOfFilesGenerated;


    @Before
    public void setUp() {
        alreadyExisted = true;
        numOfFilesGenerated = 0;
        baos = new ByteArrayOutputStream();
        outBuff = new PrintStream(baos);
        oldOut = System.out;
        System.setOut(outBuff);
    }

    @After
    public void tearDown() {
        numOfFilesGenerated = 0;
        alreadyExisted = true;
        System.setOut(oldOut);
        outBuff = null;
        baos = null;
    }

    public  boolean deleteDir(File dir)
    {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++)
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                {
                    return false;
                }
            }
        }
        // The directory is now empty or this is a file so delete it
        return dir.delete();
    }

    public void generateBigfileInDir(String pathToTheDir,long numOfBytes) {

        File theDir = new File(pathToTheDir);

// if the directory does not exist, create it
        if (!theDir.exists()) {
                alreadyExisted = false;
                theDir.mkdir();
        }
        File newfile = new File(pathToTheDir+"\\file"+ (++numOfFilesGenerated));
        FileOutputStream fos;
        BufferedOutputStream bos;
        try {
            fos = new FileOutputStream(newfile);
            bos = new BufferedOutputStream(fos);

            //here I write the to the file
            bos.close();
        }catch (Exception ex){
            System.out.println("error in writing to file in testDir class");
        }


    }

    @Test
    public  void testEncryptDecryptDir(){
        Helper helper = new Helper();
        String pathOfFirstFileForEncrypt = "c:\\files\\file1.txt";
        String pathOfFirstFileForDecrypt = "c:\\files\\encrypted\\file1.txt.encrypted";
        String pathOfFirstDecryptedFile = "c:\\files\\encrypted\\decrypted\\file1_decrypted.txt";

        Key key = new Key();
        key.generateNewKey("c:\\files");


        ArrayList<MyFile> filesForEncrypt = new ArrayList<>();
        MyFile firstFileForEncrypt = new MyFile(pathOfFirstFileForEncrypt);
        filesForEncrypt.add(firstFileForEncrypt);


        helper.doSyncActionOnDir(filesForEncrypt,"encrypt","xor",key);

        ArrayList<MyFile> filesForDecrypt = new ArrayList<>();
        MyFile firstFileForDecrypt = new MyFile(pathOfFirstFileForDecrypt);
        filesForDecrypt.add(firstFileForDecrypt);

        MyFile firstFileDecrypted = new MyFile(pathOfFirstDecryptedFile);

        assertArrayEquals("the data of the source file and the decrypted file most be the same",firstFileForEncrypt.getFileData(),firstFileDecrypted.getFileData());
        assertThat("the data of the source file and the encrypted file are not the same",firstFileForEncrypt.getFileData(),not(equalTo(firstFileForDecrypt.getFileData())));

    }


}
