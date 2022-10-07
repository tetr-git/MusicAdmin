package ui.cli.parser;

import domain_logic.MediaFileRepoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.SetRepositoryStatusListener;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ParseStorageTest {

    MediaFileRepoList mediaFileRepoList;
    EventHandler inputHandler;
    EventHandler outputHandler;
    ParseStorage parseStorage;

    @BeforeEach
    void setUp() {
        mediaFileRepoList = new MediaFileRepoList (new BigDecimal(10000000));
        inputHandler = new EventHandler();
        outputHandler = new EventHandler();
        inputHandler.add(new SetRepositoryStatusListener(mediaFileRepoList,outputHandler));
        parseStorage = new ParseStorage(inputHandler);
    }

    @Test
    void checkIfInstanceZeroIsActiveByStart() {

        assertTrue(mediaFileRepoList.getRepoByNumber(0).isActive());
    }

    @Test
    void checkTurningOffDefaultInstance() {
        parseStorage.execute("storage");

        assertFalse(mediaFileRepoList.getRepoByNumber(0).isActive());
    }

    @Test
    void checkReactivatingOfDefaultInstance() {
        parseStorage.execute("storage");
        parseStorage.execute("storage 0");

        assertTrue(mediaFileRepoList.getRepoByNumber(0).isActive());
    }

    @Test
    void checkActivatingOfTwoInstances() {
        parseStorage.execute("storage");
        parseStorage.execute("storage 0 2");

        assertTrue(mediaFileRepoList.getRepoByNumber(0).isActive()&&
                mediaFileRepoList.getRepoByNumber(2).isActive());
    }

    @Test
    void checkThreeInstances() {
        parseStorage.execute("storage");
        parseStorage.execute("storage 0 2");
        parseStorage.execute("storage 1");


        assertTrue(mediaFileRepoList.getRepoByNumber(1).isActive());
    }

}