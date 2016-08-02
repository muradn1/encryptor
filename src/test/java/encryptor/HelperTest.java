package encryptor;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.*;

import java.io.*;
import java.util.ArrayList;


/**
 * Unit test for Helper class.
 */
//@RunWith(MockitoJUnitRunner.class)
public class HelperTest {

    Helper helper;

    //@Mock
    PipedOutputStream pipeOut;
    //@Mock
    PipedInputStream in;
    //@Mock
    PrintStream out,outBuff;
    //
    ByteArrayOutputStream baos;
    //
    PrintStream oldOut;
    //
    InputStream oldIn;
    @Mock
    ArrayList<MyFile> MyFiles;
    @Mock
    ArrayList<String> myFilesPaths;

    @Before
    public void setUp() {
        helper = new Helper();
        pipeOut = new PipedOutputStream();
        in = new PipedInputStream();
        out = new PrintStream(pipeOut);

        baos = new ByteArrayOutputStream();
        outBuff = new PrintStream(baos);
        oldOut = System.out;
        oldIn = System.in;

        MyFiles = new ArrayList<MyFile>();
        myFilesPaths = new ArrayList<String>();
        //oldIn = System.in;
        System.setOut(outBuff);

        System.setIn(in);
        try {
        in.connect(pipeOut);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        helper = null;
        System.setIn(oldIn);
        System.setOut(oldOut);
        in = null;
        out = null;
        pipeOut = null;
        outBuff = null;
        baos = null;

    }

    @Test
    public void testGetUserInput() {

        int available = 0;

        try {
            //pipeOut.write('e');
            out.print("e\n");
            available = in.available();
            System.out.println("available: "+ available);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String act = helper.askUserToChooseEncryptOrDecrypt("when user insert e/E");
        assertEquals("the user chose to encrypt and the system response accordingly","encrypt",act);

        try {
            out.print("d\n");
            available = in.available();
            System.out.println("available: "+ available);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        act = helper.askUserToChooseEncryptOrDecrypt("when user insert d/D");
        assertEquals("the user chose to decrypt and the system response accordingly","decrypt",act);

        try {
            out.print("abc\n" + "e\n");
            available = in.available();
            System.out.println("available: "+ available);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        act = helper.askUserToChooseEncryptOrDecrypt("when user insert wrong input then insert d/D");
        assertThat("outPutStream contains error stream",baos.toString().contains("you have to choose whether to encrypt or decrypt."),is(true));
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    @Test
    public void testGetFileFromUser() throws Exception {


        //out.print("abc\n" + "e\n");
        File tempFile = tempFolder.newFile("newFile.txt");
        File tempFolder1 = tempFolder.newFolder("newFolder");
        String keyPath;
        boolean isDir = false;


       // System.setOut(oldOut);
       // System.out.println(tempFile.getPath());
       // System.out.println(tempFolder1.getPath());

        out.print(tempFolder1.getPath() + "\n"+ tempFile.getPath()+"\n");
        keyPath = helper.getFilePathFromUser(MyFiles,isDir,"adding path of folder then adding path of file");
        assertThat(baos.toString().contains("you have to insert a path of existing readable file (not directory)"),is(true));

        baos.reset();
        out.print("c:\\abc.txt" + "\n"+ tempFile.getPath()+"\n");
        keyPath = helper.getFilePathFromUser(MyFiles,isDir,"adding path of file that isn't exist then adding path of existing file");
        assertThat(baos.toString().contains("you have to insert a path of existing readable file (not directory)"),is(true));


    }

}
