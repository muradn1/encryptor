package encryptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by murad on 09/07/2016.
 */
public class EncryptDecryptObserverTest {

    @Mock
    EncryptDecryptObservable observable;

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
    public void testEncryptDecryptObserver(){
        observable = new EncryptDecryptObservable() {
            @Override
            public void encrypt(byte key, myFile myfile) {

            }

            @Override
            public void decrypt(byte key, myFile myfile) {

            }

            @Override
            public byte applyEncrypt(byte key, byte copiedByteFromFileData){

                return 0;
            }

            @Override
            public byte applyDecrypt(byte key, byte copiedByteFromFileData){

                return 0;
            }
        };

        observable.encrypt_decrypt_start("encryption1");


        assertThat("outPutStream contains 'encryption1'",baos.toString().contains("encryption1"),is(true));

        observable.encrypt_decrypt_end("encryption2");

        assertThat("outPutStream contains 'encryption1'",baos.toString().contains("encryption1"),is(true));

    }
}
