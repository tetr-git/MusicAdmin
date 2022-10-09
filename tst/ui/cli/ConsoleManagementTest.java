package ui.cli;

import domain_logic.MediaFileRepository;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import ui.cli.parser.ParseCreate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleManagementTest {

    @Test
    void run() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000000));
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        //inputHandler.add(new CreateMediaListener(mediaFileRepository, outputHandler));
        //inputHandler.add(new CreateUploaderListener(mediaFileRepository,outputHandler));
        ParseCreate parseCreate = new ParseCreate(inputHandler);
        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");
    }
    /*
    @Test
    void run_testswitchMode() {
        EventHandler eventHandler = new EventHandler();
        ConsoleManagement consoleManagement = new ConsoleManagement(eventHandler);
        String userInput = "1" + System.getProperty("line.separator") +
                "y" + System.getProperty("line.separator") +
                "ID001" + System.getProperty("line.separator") +
                "100" + System.getProperty("line.separator") +
                "c" + System.getProperty("line.separator") +
                "300" + System.getProperty("line.separator") +
                "2" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        String expectedOutput = " Delete mode";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        consoleManagement.run();

        String output = baos.toString();
        assertEquals(expectedOutput,output);


    }


    @Test
    public void validUserInput_ShouldResultInExpectedOutput() {
        String userInput = String.format("Dan%sVega%sdanvega@gmail.com",
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        String expected = "Dan,Vega,danvega@gmail.com";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        application.main(null); // call the main method

        String[] lines = baos.toString().split(System.lineSeparator());
        String actual = lines[lines.length-1];

        // checkout output
        assertEquals(expected,actual);
    }

     */

}
