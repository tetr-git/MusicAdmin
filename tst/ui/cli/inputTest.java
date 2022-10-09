package ui.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class inputTest {

    private final static InputStream systemIn = System.in;
    private final static PrintStream systemOut = System.out;
    private ByteArrayInputStream typeIn;
    private static ByteArrayOutputStream typeOut;

    @BeforeEach
    void setUp() throws Exception {
        typeOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(typeOut));
    }


    @Test
    final void positiveScenario() {

        String simulatedUserInput = "1" + System.getProperty("line.separator") +
                "y" + System.getProperty("line.separator") +
                "ID001" + System.getProperty("line.separator") +
                "100" + System.getProperty("line.separator") +
                "c" + System.getProperty("line.separator") +
                "300" + System.getProperty("line.separator") +
                "2" + System.getProperty("line.separator");

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
    }

    @AfterEach
    void tearDown() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }
}
