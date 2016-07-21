package encryptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by murad on 11/07/2016.
 */
public class SplitAlgorithmTest {

    SplitAlgorithm splitAlgorithm;
    @Mock
    CaesarAlgorithm caesarAlgorithm;
    @Mock
    MyFile fileForEncrypt;
    @Mock
    MyFile fileForDecrypt;
    @Mock
    MyFile decryptedFile;
    @Mock
    Key key;

    String pathOfFileForEncrypt;
    String pathOfFileForDecrypt;
    String pathOfDecryptedFile;
    String pathForKey;

    ByteArrayOutputStream baos;
    PrintStream outBuff;
    PrintStream oldOut;

    @Before
    public void setUp() {
        key = new Key();
        caesarAlgorithm = new CaesarAlgorithm();
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
        key = null;
        caesarAlgorithm = null;
    }

    @Test
    public void testDoubleAlgorithmEncryptionAndDecryption() throws Exception {

        splitAlgorithm = new SplitAlgorithm();

        pathOfFileForEncrypt = "c:\\files\\file1.txt";
        pathOfFileForDecrypt = "c:\\files\\file1.txt.encrypted";
        pathOfDecryptedFile = "c:\\files\\file1_decrypted.txt";
        pathForKey = "c:\\files";

        fileForEncrypt = new MyFile(pathOfFileForEncrypt);
        key.generateNewKey(pathForKey);


        splitAlgorithm.<CaesarAlgorithm,CaesarAlgorithm>encrypt(key,fileForEncrypt,caesarAlgorithm,null);

        fileForDecrypt = new MyFile(pathOfFileForDecrypt);

        splitAlgorithm.<CaesarAlgorithm,XorAlgorithm>decrypt(key,fileForDecrypt,caesarAlgorithm,null);

        decryptedFile = new MyFile(pathOfDecryptedFile);


        assertArrayEquals("the data of the source file and the decrypted file most be the same",fileForEncrypt.getFileData(),decryptedFile.getFileData());
        assertThat("the data of the source file and the encrypted file are not the same",fileForEncrypt.getFileData(),not(equalTo(fileForDecrypt.getFileData())));

    }
}
