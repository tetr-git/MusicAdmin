package ui.cli.parser;

import domain_logic.MediaFileRepoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.*;
import routing.parser.ParseCreate;
import routing.parser.ParseDelete;
import cli.ConsoleManagement;
import routing.parser.ParseStorage;


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
    ParseStorage parseStorage;

    @BeforeEach
    void setUp() {
        mediaFileRepoList = new MediaFileRepoList(new BigDecimal(10000000));
        inputHandler = new EventHandler();
        outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepoList, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepoList,outputHandler));
        inputHandler.add(new DeleteListener(mediaFileRepoList,outputHandler));
        inputHandler.add(new InstanceListener(mediaFileRepoList,outputHandler));
        consoleManagement = mock(ConsoleManagement.class);
        outputHandler.add(new OutputCliListener(consoleManagement));
        parseCreate = new ParseCreate(inputHandler);
        parseDelete= new ParseDelete(inputHandler);
        parseStorage = new ParseStorage(inputHandler);
    }

    @Test
    void testMultipleInstances() {
        parseStorage.execute("storage 0 1");

        parseCreate.execute("Produzent1");
        parseCreate.execute("Produzent2");

        parseDelete.execute("Produzent1");

        boolean boolOne = mediaFileRepoList.getSingleRepository(0).readUploaderList().getFirst().getName().equals("Produzent2");
        boolean boolTwo = mediaFileRepoList.getSingleRepository(1).readUploaderList().getFirst().getName().equals("Produzent2");
        assertTrue(boolOne&&boolTwo);
    }

     /*
    Test with standard repository active (repository number 0)
     */

    @Test
    void testDeleteUploader() {
        parseCreate.execute("Produzent1");
        parseCreate.execute("Produzent2");

        parseDelete.execute("Produzent1");

        assertEquals(1,mediaFileRepoList.getSingleRepository(0).readUploaderList().size());
    }

    @Test
    void testDeleteUploaderWithMediaFile() {
        parseCreate.execute("Produzent1");
        parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

        parseDelete.execute("Produzent1");

        assertEquals(0,mediaFileRepoList.getSingleRepository(0).readMediaList().size());
    }

  @Test
  void testDeleteMediaFile() {
      parseCreate.execute("Produzent1");
      parseCreate.execute("InteractiveVideo Produzent1 Lifestyle,News 500 360");

      parseDelete.execute("1");

      assertEquals(0,mediaFileRepoList.getSingleRepository(0).readMediaList().size());
  }


}