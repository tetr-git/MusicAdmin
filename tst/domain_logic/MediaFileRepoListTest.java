package domain_logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MediaFileRepoListTest {

    /*
    tested as well detail in parsers
     */

    MediaFileRepoList mediaFileRepoList;

    @BeforeEach
    void setUp() {
        mediaFileRepoList = new MediaFileRepoList(new BigDecimal(100000));
    }

    @Test
    void getRepoList() {
        assertEquals(1,mediaFileRepoList.getRepoList().size());
    }


    //todo here additional test
    @Test
    void getCopyOfRepoByNumber() {
        String[] strings = {"2"};
        mediaFileRepoList.changeStateAllRepositories(strings);

        assertEquals(2,mediaFileRepoList.getCopyOfRepoByNumber(2).getNumberOfRepository());
    }

    @Test
    void detachAllRepositories() {
        String[] setActive = {"storage", "2", "1", "0"};
        mediaFileRepoList.changeStateAllRepositories(setActive);
        mediaFileRepoList.detachAllRepositories();
        boolean checkIfRepoActive = false;
        for (MediaFileRepository mediaFileRepository : mediaFileRepoList.getRepoList()) {
            if (mediaFileRepository.isActiveRepository()) {
                checkIfRepoActive = true;
                break;
            }
        }
        assertFalse(checkIfRepoActive);
    }

    @Test
    void changeStateAllRepositoriesAddNewInstances() {
        String[] setActive = {"storage", "2", "1", "0"};
        boolean checkIfRepoIsActive = false;
        mediaFileRepoList.changeStateAllRepositories(setActive);
        for (MediaFileRepository mediaFileRepository : mediaFileRepoList.getRepoList()) {
            if (mediaFileRepository.isActiveRepository()) {
                checkIfRepoIsActive = true;
            } else {
                fail();
            }
        }
        assertTrue(checkIfRepoIsActive);
    }
    @Test
    void setDeactivateAllAndActivateAnotherNewInstance() {
        String[] setActive = {"storage", "2", "1", "0"};
        mediaFileRepoList.changeStateAllRepositories(setActive);
        boolean checkIfRepoIsActive = true;

        String[] deactivateAllAndActivateAnotherNewInstance = {"storage","3"};
        mediaFileRepoList.changeStateAllRepositories(deactivateAllAndActivateAnotherNewInstance);
        for (MediaFileRepository mediaFileRepository :mediaFileRepoList.getRepoList()) {
            if (!(mediaFileRepository.getNumberOfRepository()==3)) {
                if (!mediaFileRepository.isActiveRepository()) {
                    checkIfRepoIsActive = false;
                } else {
                    fail();
                }
            }
        }
        assertTrue(!checkIfRepoIsActive&&mediaFileRepoList.getCopyOfRepoByNumber(3).isActiveRepository());
    }
    //todo könnte eventuell besser
    @Test
    void checkIfJosFileIsCreated() throws FileNotFoundException {
        String fileNameJos = "mediaFileRepoJos";
        File fileJos = new File(fileNameJos);

        mediaFileRepoList.safeJos();
        assertTrue(fileJos.exists());

        if (!fileJos.delete())
            throw new FileNotFoundException( "File couldn't be deleted!" );
    }

    @Test
    void loadJos() throws FileNotFoundException {
        String fileNameJos = "mediaFileRepoJos";
        File fileJos = new File(fileNameJos);
        mediaFileRepoList.safeJos();

        String[] changeStatesAfterSave = {"storage","1"};
        //turns off instance 0 -> isActiveRepository false;
        mediaFileRepoList.changeStateAllRepositories(changeStatesAfterSave);
        mediaFileRepoList.loadJos();

        assertTrue(mediaFileRepoList.getCopyOfRepoByNumber(0).isActiveRepository());

        if (!fileJos.delete())
            throw new FileNotFoundException( "File couldn't be deleted!" );
    }
}