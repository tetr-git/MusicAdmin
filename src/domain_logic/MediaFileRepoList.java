package domain_logic;

import java.math.BigDecimal;
import java.util.LinkedList;

import static util.TypeConverter.addInteger;
import static util.TypeConverter.isNumericInteger;

public class MediaFileRepoList {
    private LinkedList<MediaFileRepository> repoList = new LinkedList<>();
    private final BigDecimal maxCapacity;

    public MediaFileRepoList(BigDecimal maxCapacity) {
        this.maxCapacity = maxCapacity;
        MediaFileRepository repoZero = new MediaFileRepository(maxCapacity);
        repoList.add(repoZero);
        repoZero.setListNumber(0);
        repoZero.setActive(true);
    }

    public LinkedList<MediaFileRepository> getRepoList() {
        return repoList;
    }

    private void addToList(int index) {
        if (checkRepoAtIndexExists(index)) {
            MediaFileRepository newRepo = new MediaFileRepository(maxCapacity);
            repoList.add(newRepo);
            newRepo.setListNumber(index);
        }
    }

    public int getCurrentNumberOfRepos() {
        return repoList.size();
    }

    public MediaFileRepository getRepoByIndex(int index) {
        if (checkRepoAtIndexExists(index)) {
            return repoList.get(index);
        }
        return repoList.get(0);
    }
    //only for testing
    private boolean checkRepoAtIndexExists(int index) {
        if (!repoList.isEmpty()) {
            for (MediaFileRepository repo : repoList) {
                if (repo.getListNumber()==index) {
                    return true;
                }
            }
        }
        return false;
    }

    public void detachAllRepositories() {
        if(!repoList.isEmpty()) {
            for (MediaFileRepository repo : repoList) {
                repo.setActive(false);
            }
        }
    }

    public void changeStateAllRepositories(String[] inputRepoNumbers) {
        for (String repoString : inputRepoNumbers) {
            if (isNumericInteger(repoString)) {
                int repoInt = addInteger(repoString);
                if (repoInt>-1) {
                    for (MediaFileRepository addRepo : repoList) {
                        if (!(addRepo.getListNumber()==repoInt)) {
                            addToList(repoInt);
                        }
                    }
                    for (MediaFileRepository repo : repoList) {
                        if (repo.getListNumber()==repoInt) {
                            repo.setActive(true);
                        } else {
                            repo.setActive(false);
                        }
                    }
                }
            }
        }
    }


    //todo put saveJos here
}