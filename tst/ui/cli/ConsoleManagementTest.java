package ui.cli;

import domain_logic.MediaFileRepository;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.CreateMediaListener;
import routing.listener.CreateUploaderListener;
import ui.cli.parser.ParseCreate;

import java.math.BigDecimal;

class ConsoleManagementTest {

    @Test
    void run() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000000));
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepository, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepository,outputHandler));
        ParseCreate parseCreate = new ParseCreate(inputHandler);
        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

    }
}