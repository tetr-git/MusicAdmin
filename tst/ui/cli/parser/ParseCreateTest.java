package ui.cli.parser;

import domain_logic.MediaFileRepository;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.CreateMediaListener;
import routing.listener.CreateUploaderListener;
import ui.cli.parser.ParseCreate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ParseCreateTest {

    @Test
    void execute() {
        MediaFileRepository mediaFileRepository = mock(MediaFileRepository.class);
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepository, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepository,outputHandler));
        ParseCreate parseCreate = new ParseCreate(inputHandler);

        parseCreate.execute("Produzent1");

        verify(mediaFileRepository).insertUploaderFromString("Produzent1");
    }

    @Test
    void parseToCreateUploaderEvent() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000000));
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepository, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepository,outputHandler));
        ParseCreate parseCreate = new ParseCreate(inputHandler);

        parseCreate.execute("Produzent1");

        assertEquals("Produzent1",mediaFileRepository.readUploaderList().getFirst().getName());
    }

    @Test
    void parseToCreateMediaEvent() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000000));
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepository, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepository,outputHandler));
        ParseCreate parseCreate = new ParseCreate(inputHandler);

        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

        assertEquals(1,mediaFileRepository.readMediaList().size());
    }

    @Test
    void parseMultipleUploaderStrings() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000000));
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepository, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepository,outputHandler));
        ParseCreate parseCreate = new ParseCreate(inputHandler);

        parseCreate.execute("Produzent1");
        parseCreate.execute("Produzent1");
        parseCreate.execute("Produzent2");
        parseCreate.execute("Produzent3");

        assertEquals(3,mediaFileRepository.readUploaderList().size());
    }

    @Test
    void parseAllPossibleMediaTypeStrings() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000000));
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepository, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepository,outputHandler));
        ParseCreate parseCreate = new ParseCreate(inputHandler);

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

        assertEquals(14,mediaFileRepository.readMediaList().size());
    }
}