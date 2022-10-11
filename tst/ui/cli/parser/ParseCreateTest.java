package ui.cli.parser;

import domain_logic.MediaFileRepoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.CreateMediaListener;
import routing.listener.CreateUploaderListener;
import routing.listener.InstanceListener;
import routing.parser.ParseCreate;
import routing.parser.ParseStorage;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ParseCreateTest {

    MediaFileRepoList mediaFileRepoList;
    EventHandler inputHandler;
    EventHandler outputHandler;
    ParseCreate parseCreate;
    ParseStorage parseStorage;

    @BeforeEach
    void setUp() {
        mediaFileRepoList = new MediaFileRepoList(new BigDecimal(10000000));
        inputHandler = new EventHandler();
        outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepoList, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepoList,outputHandler));
        inputHandler.add(new InstanceListener(mediaFileRepoList,outputHandler));
        parseCreate = new ParseCreate(inputHandler);
        parseStorage = new ParseStorage(inputHandler);
    }

    @Test
    void checkMultiInstance() {

        parseStorage.execute("storage 0 1");
        parseCreate.execute("Produzent1");
        boolean boolOne = mediaFileRepoList.getSingleRepository(0).readUploaderList().getFirst().getName().equals("Produzent1");
        boolean boolTwo = mediaFileRepoList.getSingleRepository(1).readUploaderList().getFirst().getName().equals("Produzent1");
        assertTrue(boolOne&&boolTwo);
    }

     /*
    Test with standard repository active (repository number 1)
     */

    @Test
    void checkParseCreateUploaderWithStandardRepositoryActive() {
        parseCreate.execute("Produzent1");

        assertEquals("Produzent1",mediaFileRepoList.getSingleRepository(0).readUploaderList().getFirst().getName());
    }

    @Test
    void parseToCreateUploaderEvent() {
        parseCreate.execute("Produzent1");

        assertEquals("Produzent1",mediaFileRepoList.getSingleRepository(0).readUploaderList().getFirst().getName());
    }

    @Test
    void parseToCreateMediaEvent() {
        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

        assertEquals(1,mediaFileRepoList.getSingleRepository(0).readMediaList().size());
    }

    @Test
    void parseMultipleUploaderStrings() {
        parseCreate.execute("Produzent1");
        parseCreate.execute("Produzent1");
        parseCreate.execute("Produzent2");
        parseCreate.execute("Produzent3");

        assertEquals(3,mediaFileRepoList.getSingleRepository(0).readUploaderList().size());
    }

    @Test
    void parseAllPossibleMediaTypeStrings() {
        parseCreate.execute("Produzent1");

        parseCreate.execute("audio Produzent1 Animal,Tutorial,Lifestyle,News 500 360 550 ");
        parseCreate.execute("audio Produzent1 Lifestyle,News 500 360");
        parseCreate.execute("audiovideo Produzent1 Lifestyle,News 500 360 1 1");
        parseCreate.execute("audiovideo Produzent1 Lifestyle,News 500 360");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360 1 1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");
        parseCreate.execute("licensedaudio Produzent1 Lifestyle,News 500 360 1 Zwei");
        parseCreate.execute("licensedaudio Produzent1 Lifestyle,News 500 360");
        parseCreate.execute("licensedaudiovideo Produzent1 Lifestyle,News 500 360 1 drei 4");
        parseCreate.execute("licensedaudiovideo Produzent1 Lifestyle,News 500 360");
        parseCreate.execute("licensedvideo Produzent1 Lifestyle,News 500 360 Hello 2");
        parseCreate.execute("licensedvideo Produzent1 Lifestyle,News 500 360");
        parseCreate.execute("video Produzent1 Lifestyle 500 360 2");
        parseCreate.execute("video Produzent1 Lifestyle,News 500 360");

        assertEquals(14,mediaFileRepoList.getSingleRepository(0).readMediaList().size());
    }

    @Test
    void parseExamplesFromRequirements() {
        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 5000 3600 Abstimmung 1080");
        parseCreate.execute("LicensedAudioVideo Produzent1 , 8000 600 EdBangerRecords 44100 720");
        parseCreate.execute("LicensedVideo Produzent1 News 1000 1800");

        assertEquals(3,mediaFileRepoList.getSingleRepository(0).readMediaList().size());
    }


}