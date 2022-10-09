import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CliTest {

    /*
    sources for change of System.in and System.out
    https://www.danvega.dev/blog/2020/12/16/testing-standard-in-out-java/
    https://sehun.me/test-user-input-without-a-mock-framework-junit-69b8b052b6f2
     */

    @Test
    void CliWithoutNet_SwitchModeCli() {
        String userInput = ":d" + System.getProperty("line.separator") +
                ":exit" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        String expectedOutput = "create:     # delete:     # ";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        String[] arg = {"1000000"};
        Cli.main(arg);

        String output = baos.toString();
        assertEquals(expectedOutput,output);
    }

    @Test
    void CliWithoutNet_addUploader() {
        String userInput = "Hans" + System.getProperty("line.separator") +
                ":exit" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        String expectedOutput = "create:     # Repository[0] added uploader Hans";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        String[] arg = {"1000000"};
        Cli.main(arg);

        String[] lines = baos.toString().split(System.lineSeparator());
        String output = lines[lines.length-2];
        assertEquals(expectedOutput,output);
    }

    @Test
    void CliWithoutNet_addUploaderAndMedia() {
        String userInput = "Produzent1" + System.getProperty("line.separator") +
                "InteractiveVideo Produzent1 Lifestyle,News 5000 3600 Abstimmung 1080" + System.getProperty("line.separator") +
                ":exit" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        String expectedOutput = "Repository[0] added Media interactivevideo";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        String[] arg = {"1000000"};
        Cli.main(arg);

        String[] lines = baos.toString().split(System.lineSeparator());
        String output = lines[lines.length-2];
        assertEquals(expectedOutput,output);
    }

    @Test
    void CliWithoutNet_DisableAllStorageInstances() {
        String userInput = "Produzent1" + System.getProperty("line.separator") +
                "InteractiveVideo Produzent1 Lifestyle,News 5000 3600 Abstimmung 1080" + System.getProperty("line.separator") +
                ":s" + System.getProperty("line.separator") +
                "storage" + System.getProperty("line.separator") +
                ":exit" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        String expectedOutput = "create:     # storage:    # all instances deactivated";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        String[] arg = {"1000000"};
        Cli.main(arg);

        String[] lines = baos.toString().split(System.lineSeparator());
        String output = lines[lines.length-2];
        assertEquals(expectedOutput,output);
    }

    @Test
    void CliWithoutNet_ReadFromNewInstance() {
        String userInput = "Produzent1" + System.getProperty("line.separator") +
                "InteractiveVideo Produzent1 Lifestyle,News 5000 3600 Abstimmung 1080" + System.getProperty("line.separator") +
                ":s" + System.getProperty("line.separator") +
                "storage 1" + System.getProperty("line.separator") +
                ":r" + System.getProperty("line.separator") +
                "uploader" + System.getProperty("line.separator") +
                ":exit" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        String expectedOutput = "storage:    # read:       # Repository[1] is empty";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        String[] arg = {"1000000"};
        Cli.main(arg);

        String[] lines = baos.toString().split(System.lineSeparator());
        String output = lines[lines.length-2];
        assertEquals(expectedOutput,output);
    }

    @Test
    void CliWithoutNet_ReadProducerWithNumberOfUploadedFiles() {
        String userInput = "Produzent1" + System.getProperty("line.separator") +
                "InteractiveVideo Produzent1 Lifestyle,News 5000 3600 Abstimmung 1080" + System.getProperty("line.separator") +
                "Produzent2" + System.getProperty("line.separator") +
                "InteractiveVideo Produzent2 Lifestyle,News 5000 3600 Abstimmung 1080" + System.getProperty("line.separator") +
                "Audio Produzent2 Lifestyle 5000 3600 " + System.getProperty("line.separator") +
                ":r" + System.getProperty("line.separator") +
                "uploader" + System.getProperty("line.separator") +
                ":exit" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        String expectedOutput = "Produzent1\t1";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        String[] arg = {"1000000"};
        Cli.main(arg);

        String[] lines = baos.toString().split(System.lineSeparator());
        String output = lines[lines.length-3];
        assertEquals(expectedOutput,output);
    }

    @Test
    void CliWithoutNet_ReadProducerWithNumberOfUploadedFilesEmpty() {
        String userInput = ":r" + System.getProperty("line.separator") +
                "uploader" + System.getProperty("line.separator") +
                ":exit" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        String expectedOutput = "create:     # read:       # Repository[0] is empty";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        String[] arg = {"1000000"};
        Cli.main(arg);

        String[] lines = baos.toString().split(System.lineSeparator());
        String output = lines[lines.length-2];
        assertEquals(expectedOutput,output);
    }

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
    }
}