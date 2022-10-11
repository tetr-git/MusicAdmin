package ui.cli.parser;

import domain_logic.MediaFileRepoList;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.ChangeListener;
import routing.listener.OutputCliListener;
import routing.listener.CreateMediaListener;
import routing.listener.CreateUploaderListener;
import cli.ConsoleManagement;
import routing.parser.ParseCreate;
import routing.parser.ParseUpdate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ParseUpdateTest {

    @Test
    void execute() {
        MediaFileRepoList mediaFileRepoList = new MediaFileRepoList(new BigDecimal(1000000000));
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new ChangeListener(mediaFileRepoList,outputHandler));
        inputHandler.add(new CreateMediaListener(mediaFileRepoList,outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepoList,outputHandler));
        ConsoleManagement consoleManagement = mock(ConsoleManagement.class);
        outputHandler.add(new OutputCliListener(consoleManagement));
        ParseCreate parseCreate = new ParseCreate(inputHandler);
        ParseUpdate parseUpdate = new ParseUpdate(inputHandler);

        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

        parseUpdate.execute("1");

        assertEquals(1,mediaFileRepoList.getSingleRepository(0).readMediaList().getFirst().getAccessCount());
    }
}