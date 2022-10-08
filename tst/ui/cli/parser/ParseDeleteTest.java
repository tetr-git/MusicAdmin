package ui.cli.parser;

import domain_logic.MediaFileRepoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.CliOutputListener;
import routing.listener.CreateMediaListener;
import routing.listener.CreateUploaderListener;
import routing.listener.DeleteListener;
import ui.cli.ConsoleManagement;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ParseDeleteTest {

    MediaFileRepoList mediaFileRepoList;
    ConsoleManagement consoleManagement;
    EventHandler inputHandler;
    EventHandler outputHandler;
    ParseCreate parseCreate;
    ParseDelete parseDelete;

    @BeforeEach
    void setUp() {
        mediaFileRepoList = new MediaFileRepoList(new BigDecimal(10000000));
        inputHandler = new EventHandler();
        outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepoList, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepoList,outputHandler));
        inputHandler.add(new DeleteListener(mediaFileRepoList,outputHandler));
        consoleManagement = mock(ConsoleManagement.class);
        outputHandler.add(new CliOutputListener(consoleManagement));
        parseCreate = new ParseCreate(inputHandler);
        parseDelete= new ParseDelete(inputHandler);
    }

     /*
    Test with standard repository active (repository number 0)
    todo check text output with one or more repositories
     */

    @Test
    void testDeleteUploader() {
        parseCreate.execute("Produzent1");
        parseCreate.execute("Produzent2");

        parseDelete.execute("Produzent1");

        assertEquals(1,mediaFileRepoList.getCopyOfRepoByNumber(0).readUploaderList().size());
    }

    @Test
    void testDeleteUploaderWithMediaFile() {
        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

        parseDelete.execute("Produzent1");

        assertEquals(0,mediaFileRepoList.getCopyOfRepoByNumber(0).readMediaList().size());
    }

  @Test
  void testDeleteMediaFile() {
      parseCreate.execute("Produzent1");
      parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

      parseDelete.execute("1");

      assertEquals(0,mediaFileRepoList.getCopyOfRepoByNumber(0).readMediaList().size());
  }


}