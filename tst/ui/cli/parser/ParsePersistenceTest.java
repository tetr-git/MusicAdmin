package ui.cli.parser;

import domain_logic.MediaFileRepository;
import domain_logic.enums.Tag;
import domain_logic.files.AudioVideoFile;
import domain_logic.files.MediaFile;
import domain_logic.producer.UploaderImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.*;
import ui.cli.ConsoleManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ParsePersistenceTest {
    /*
    todo Speicherzugriff auf System laut Anforderungen nicht erlaubt,
    Datei wird im Projektordner angelegt sollte daher zumindest Betriebssystem unabhängig sein
     */

    MediaFileRepository mediaFileRepository;
    EventHandler inputHandler;
    EventHandler outputHandler;
    ConsoleManagement consoleManagement;

    @BeforeEach
    void setUp() {
        mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000000));
        inputHandler = new EventHandler();
        outputHandler = new EventHandler();
        inputHandler.add(new SaveListener(mediaFileRepository,outputHandler));
        inputHandler.add(new LoadListener(mediaFileRepository,outputHandler));
        consoleManagement = mock(ConsoleManagement.class);
        outputHandler.add(new CliOutputListener(consoleManagement));
    }

    @Test
    void testSaveJos() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioVideoFile = new AudioVideoFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.Animal)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(300),
                320,
                1920);;

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioVideoFile);

        ParsePersistence parsePersistence = new ParsePersistence(inputHandler);

        parsePersistence.execute("Jos Save");

        verify(consoleManagement).writeToConsole("Jos saved");
    }

    @Test
    void testLoadJos() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioVideoFile = new AudioVideoFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.Animal)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(300),
                320,
                1920);;
        ParsePersistence parsePersistence = new ParsePersistence(inputHandler);

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioVideoFile);
        if (!mediaFileRepository.safeJos()) {
            fail();
        }

        parsePersistence.execute("Jos Load");

        assertEquals(1,mediaFileRepository.getCurrentNumberOfMediaElements());
    }

    @Test
    void testLoadJosCliOutput() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioVideoFile = new AudioVideoFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.Animal)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(300),
                320,
                1920);;
        ParsePersistence parsePersistence = new ParsePersistence(inputHandler);

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioVideoFile);
        if (!mediaFileRepository.safeJos()) {
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