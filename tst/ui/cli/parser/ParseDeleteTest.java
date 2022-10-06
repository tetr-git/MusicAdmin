package ui.cli.parser;

import domain_logic.MediaFileRepository;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.CreateMediaListener;
import routing.listener.CreateUploaderListener;
import routing.listener.DeleteListener;
import ui.cli.parser.ParseCreate;
import ui.cli.parser.ParseDelete;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ParseDeleteTest {

    @Test
    void execute() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000000));
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new DeleteListener(mediaFileRepository,outputHandler));
        ParseDelete parseDelete= new ParseDelete(inputHandler);

        mediaFileRepository.insertUploaderFromString("Produzent1");
        mediaFileRepository.insertUploaderFromString("Produzent2");

        parseDelete.execute("Produzent1");

        assertEquals(1,mediaFileRepository.readUploaderList().size());
    }
    //todo could be better
    @Test
    void testForAddingMediaFiles() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000000));
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepository, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepository,outputHandler));
        inputHandler.add(new DeleteListener(mediaFileRepository,outputHandler));
        ParseDelete parseDelete= new ParseDelete(inputHandler);
        ParseCreate parseCreate = new ParseCreate(inputHandler);

        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

        parseDelete.execute("Produzent1");

        assertEquals(0,mediaFileRepository.readMediaList().size());
    }
}