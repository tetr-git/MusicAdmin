package routing.listener;

import domain_logic.MediaFileRepository;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import ui.cli.ParseCreate;
import ui.cli.ParseDelete;
import ui.cli.ParseUpdate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ChangeListenerTest {

    @Test
    void onEvent() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000000));
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new ChangeListener(mediaFileRepository,outputHandler));
        inputHandler.add(new CreateMediaListener(mediaFileRepository,outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepository,outputHandler));
        ParseCreate parseCreate = new ParseCreate(inputHandler);
        ParseUpdate parseUpdate = new ParseUpdate(inputHandler);

        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

        parseUpdate.execute("1");

        assertEquals(1,mediaFileRepository.readMediaList().getFirst().getAccessCount());
    }
}