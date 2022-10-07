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
        return new LinkedList<>(repoList);
    }

    private void addToList(int index) {
        if (!checkRepoAtIndexExists(index)) {
            MediaFileRepository newRepo = new MediaFileRepository(maxCapacity);
            repoList.add(newRepo);
            newRepo.setListNumber(index);
        }
    }

    /*
    //todo just for testing used
     */
    public MediaFileRepository getRepoByNumber(int number) {
        if (checkRepoAtIndexExists(number)) {
            for (MediaFileRepository repository : repoList) {
                if (repository.getListNumber()==number){
                    return repository;
                }
            }
        }
        throw new RuntimeException("Wrong Repository Number");
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
        for (MediaFileRepository repo : repoList) {
            repo.setActive(false);
        }
        for (String repoString : inputRepoNumbers) {
            if (isNumericInteger(repoString)) {
                int repoInt = addInteger(repoString);
                if (repoInt > -1) {
                    boolean add = false;
                    for (MediaFileRepository addRepo : repoList) {
                        if (!(addRepo.getListNumber() == repoInt)) {
                            add = true;
                        }
                    }
                    if (add) {
                        addToList(repoInt);
                    }
                    for (MediaFileRepository repo : repoList) {
                        if (repo.getListNumber() == repoInt) {
                            repo.setActive(true);
                        }
                    }
                }
            }
        }
    }
    //todo put saveJos here
}