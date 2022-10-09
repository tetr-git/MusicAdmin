package ui.cli.parser;

import domain_logic.MediaFileRepoList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.*;
import routing.parser.ParseCreate;
import routing.parser.ParsePersistence;
import ui.cli.ConsoleManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParsePersistenceTest {
    /*
    todo Speicherzugriff auf System laut Anforderungen nicht erlaubt,
    Datei wird im Projektordner angelegt sollte daher zumindest Betriebssystem unabhängig sein
     */
    MediaFileRepoList mediaFileRepoList;
    ConsoleManagement consoleManagement;
    EventHandler inputHandler;
    EventHandler outputHandler;
    ParseCreate parseCreate;
    ParsePersistence parsePersistence;

    @BeforeEach
    void setUp() {
        mediaFileRepoList = new MediaFileRepoList(new BigDecimal(1000000000));
        inputHandler = new EventHandler();
        outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepoList, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepoList,outputHandler));
        inputHandler.add(new SaveListener(mediaFileRepoList,outputHandler));
        inputHandler.add(new LoadListener(mediaFileRepoList,outputHandler));
        consoleManagement = mock(ConsoleManagement.class);
        outputHandler.add(new CliOutputListener(consoleManagement));
        parseCreate = new ParseCreate(inputHandler);
        parsePersistence = new ParsePersistence(inputHandler);
    }

     /*
    Test with standard repository active (repository number 1)
    todo check text output with one or more repositories
     */

    @Test
    void testSaveJos() {
        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

        parsePersistence.execute("Jos Save");

        parsePersistence.execute("Jos Load");


    }

    @Test
    void testSaveJosCliOutput() {
        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

        parsePersistence.execute("Jos Save");

        verify(consoleManagement).writeToConsole("Jos saved");
    }

    @Test
    void testLoadJos() {
        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

        if (!mediaFileRepoList.safeJos()) {
            fail();
        }

        parsePersistence.execute("Jos Load");

        assertEquals(1,mediaFileRepoList.getCopyOfRepoByNumber(0).getCurrentNumberOfMediaElements());
    }

    @Test
    void testLoadJosCliOutput() {
        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

        if (!mediaFileRepoList.safeJos()) {
            fail();
        }

        parsePersistence.execute("Jos Load");

        verify(consoleManagement).writeToConsole("Jos Loaded");
    }

    @AfterEach
    void tearDown() throws FileNotFoundException {
        File file = new File("mediaFileRepoJos");
        if (!file.delete())
            throw new FileNotFoundException( "File couldn't be deleted!" );
    }
}