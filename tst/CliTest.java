import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CliTest {

    @Test
    void SwitchModeCli() {
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
    void addUploader() {
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
    void addUploaderAndMedia() {
        String userInput = "Hans" + System.getProperty("line.separator") +
                "Hans" + System.getProperty("line.separator") +
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

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
    }
}