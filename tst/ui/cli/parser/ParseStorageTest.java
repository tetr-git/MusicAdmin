package ui.cli.parser;

import domain_logic.MediaFileRepoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.RepositoryListener;

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
        inputHandler.add(new RepositoryListener(mediaFileRepoList,outputHandler));
        parseStorage = new ParseStorage(inputHandler);
    }

    @Test
    void checkIfInstanceZeroIsActiveByStart() {

        assertTrue(mediaFileRepoList.getCopyOfRepoByNumber(0).isActiveRepository());
    }

    @Test
    void checkTurningOffDefaultInstance() {
        parseStorage.execute("storage");

        assertFalse(mediaFileRepoList.getCopyOfRepoByNumber(0).isActiveRepository());
    }

    @Test
    void checkReactivatingOfDefaultInstance() {
        parseStorage.execute("storage");
        parseStorage.execute("storage 0");

        assertTrue(mediaFileRepoList.getCopyOfRepoByNumber(0).isActiveRepository());
    }

    @Test
    void checkActivatingOfTwoInstances() {
        parseStorage.execute("storage");
        parseStorage.execute("storage 1 2");

        assertTrue(mediaFileRepoList.getCopyOfRepoByNumber(1).isActiveRepository()&&
                mediaFileRepoList.getCopyOfRepoByNumber(2).isActiveRepository());
    }

    @Test
    void checkThreeInstances() {
        parseStorage.execute("storage");
        parseStorage.execute("storage 0 2");
        parseStorage.execute("storage 1");

        assertTrue(mediaFileRepoList.getCopyOfRepoByNumber(1).isActiveRepository() &&
                !(mediaFileRepoList.getCopyOfRepoByNumber(0).isActiveRepository())) ;
    }

   @Test
   void checkRequirementExampleOne() {
        parseStorage.execute("storage 0");

       assertTrue(mediaFileRepoList.getCopyOfRepoByNumber(0).isActiveRepository() &&
               mediaFileRepoList.getRepoList().size()==1);
   }

    @Test
    void checkRequirementExampleTwo() {
        parseStorage.execute("storage 2");

        assertTrue(mediaFileRepoList.getCopyOfRepoByNumber(2).isActiveRepository() &&
                (!mediaFileRepoList.getCopyOfRepoByNumber(0).isActiveRepository()));
    }

    @Test
    void checkRequirementExampleThree() {
        parseStorage.execute("storage 2 1");

        boolean repoZero = mediaFileRepoList.getCopyOfRepoByNumber(0).isActiveRepository();
        boolean repoOne = mediaFileRepoList.getCopyOfRepoByNumber(1).isActiveRepository();
        boolean repoTwo = mediaFileRepoList.getCopyOfRepoByNumber(2).isActiveRepository();

        assertTrue(!(repoZero)&&repoOne&&repoTwo);
    }
}