package ui.cli.parser;

import domain_logic.MediaFileRepoList;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.CreateMediaListener;
import routing.listener.CreateUploaderListener;
import routing.listener.SetRepositoryStatusListener;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ParseStorageTest {

    @Test
    void execute() {
        MediaFileRepoList mediaFileRepoList = new MediaFileRepoList (new BigDecimal(10000000));
        EventHandler inputHandler = new EventHandler();
        EventHandler outputHandler = new EventHandler();
        inputHandler.add(new CreateMediaListener(mediaFileRepoList, outputHandler));
        inputHandler.add(new CreateUploaderListener(mediaFileRepoList,outputHandler));
        inputHandler.add(new SetRepositoryStatusListener(mediaFileRepoList,outputHandler));
        ParseCreate parseCreate = new ParseCreate(inputHandler);
        ParseStorage parseStorage = new ParseStorage(inputHandler);



    }
}