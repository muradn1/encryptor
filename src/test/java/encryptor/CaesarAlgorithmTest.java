package encryptor;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.*;

/**
 * Created by murad on 05/07/2016.
 */
public class CaesarAlgorithmTest {

    CaesarAlgorithm caesar;
    @Mock
    myFile fileForEncrypt;
    @Mock
    myFile fileForDecrypt;
    @Mock
    myFile decryptedFile;

    byte key;
    String pathOfFileForEncrypt;
    String pathOfFileForDecrypt;
    String pathOfDecryptedFile;

    ByteArrayOutputStream baos;
    PrintStream outBuff;
    PrintStream oldOut;

    @Before
    public void setUp() {
        baos = new ByteArrayOutputStream();
        outBuff = new PrintStream(baos);
        oldOut = System.out;
        System.setOut(outBuff);
    }

    @After
    public void tearDown() {
        System.setOut(oldOut);
        outBuff = null;
        baos = null;
    }

    @Test
    public void testCaesarEncryptionAndDecryption() {

        caesar = new CaesarAlgorithm();

        pathOfFileForEncrypt = "c:\\files\\file1.txt";
        pathOfFileForDecrypt = "c:\\files\\file1.txt.encrypted";
        pathOfDecryptedFile = "c:\\files\\file1_decrypted.txt";

        fileForEncrypt = new myFile(pathOfFileForEncrypt);

        key =1;

        caesar.encrypt(key,fileForEncrypt);

        fileForDecrypt = new myFile(pathOfFileForDecrypt);

        caesar.decrypt(key,fileForDecrypt);

        decryptedFile = new myFile(pathOfDecryptedFile);


        assertArrayEquals("the data of the source file and the decrypted file most be the same",fileForEncrypt.getFileData(),decryptedFile.getFileData());
        assertThat("the data of the source file and the encrypted file are not the same",fileForEncrypt.getFileData(),not(equalTo(fileForDecrypt.getFileData())));

    }




}
