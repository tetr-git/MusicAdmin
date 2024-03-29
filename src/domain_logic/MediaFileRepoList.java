package domain_logic;

import domain_logic.files.MediaFile;
import domain_logic.producer.Uploader;
import observer.CapacityObserver;
import observer.OberserverTyp;
import observer.Observer;
import observer.TagObserver;

import java.io.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.TypeConverter.addInteger;
import static util.TypeConverter.isNumericInteger;

public class MediaFileRepoList implements Serializable {
    static final long serialVersionUID = 1L;
    private final Lock mItemLock = new ReentrantLock();
    private final String fileNameJos = "mediaFileRepoJos";
    //observerFunctionality
    private final List<OberserverTyp> oberserverTypeEnumList = new LinkedList<>();
    private LinkedList<MediaFileRepository> repoList = new LinkedList<>();
    private BigDecimal maxCapacity;

    public MediaFileRepoList(BigDecimal maxCapacity) {
        this.maxCapacity = maxCapacity;
        MediaFileRepository repoZero = new MediaFileRepository(maxCapacity);
        repoList.add(repoZero);
        repoZero.setNumberOfRepository(0);
        repoZero.setActiveRepository(true);
        attachCurrentObserversToNewInstance(0);
    }

    public LinkedList<MediaFileRepository> getRepoList() {
        return new LinkedList<>(repoList);
    }

    public BigDecimal getMaxCapacity() {
        return maxCapacity;
    }

    private void addToList(int index) {
        if (!checkRepoAtIndexExists(index)) {
            MediaFileRepository newRepo = new MediaFileRepository(maxCapacity);
            repoList.add(newRepo);
            newRepo.setNumberOfRepository(index);
            attachCurrentObserversToNewInstance(index);
        }
    }

    public MediaFileRepository getSingleRepository(int number) {
        MediaFileRepository copyRepo = new MediaFileRepository(maxCapacity);
        if (checkRepoAtIndexExists(number)) {
            for (MediaFileRepository originalRepo : repoList) {
                if (originalRepo.getNumberOfRepository() == number) {
                    copyRepo.setNumberOfRepository(originalRepo.getNumberOfRepository());
                    copyRepo.setActiveRepository(originalRepo.isActiveRepository());
                    if (!originalRepo.readUploaderList().isEmpty()) {
                        for (Uploader u : originalRepo.readUploaderList()) {
                            copyRepo.insertUploaderFromString(u.getName());
                        }
                    }
                    if (!originalRepo.readMediaList().isEmpty()) {
                        for (MediaFile mediaFile : originalRepo.readMediaList()) {
                            copyRepo.insertMediaFile(mediaFile);
                        }
                    }
                    if (!originalRepo.getObserverList().isEmpty()) {
                        for (Observer observer : originalRepo.getObserverList()) {
                            copyRepo.attachObserver(observer);
                        }
                    }
                }
            }
        }
        return copyRepo;
    }

    private boolean checkRepoAtIndexExists(int index) {
        if (!repoList.isEmpty()) {
            for (MediaFileRepository repo : repoList) {
                if (repo.getNumberOfRepository() == index) {
                    return true;
                }
            }
        }
        return false;
    }

    //jos functionality

    public void detachAllRepositories() {
        if (!repoList.isEmpty()) {
            for (MediaFileRepository repo : repoList) {
                repo.setActiveRepository(false);
            }
        }
    }

    public void changeStateAllRepositories(String[] inputRepoNumbers) {
        for (MediaFileRepository repo : repoList) {
            repo.setActiveRepository(false);
        }
        for (String repoString : inputRepoNumbers) {
            if (isNumericInteger(repoString)) {
                int repoInt = addInteger(repoString);
                if (repoInt > -1 && repoInt < 3) {
                    boolean add = false;
                    for (MediaFileRepository addRepo : repoList) {
                        if (!(addRepo.getNumberOfRepository() == repoInt)) {
                            add = true;
                            break;
                        }
                    }
                    if (add) {
                        addToList(repoInt);
                    }
                    for (MediaFileRepository repo : repoList) {
                        if (repo.getNumberOfRepository() == repoInt) {
                            repo.setActiveRepository(true);
                        }
                    }
                }
            }
        }
    }

    public boolean safeJos() {
        mItemLock.lock();
        try {
            File fileJos = new File(fileNameJos);
            if (!fileJos.exists()) {
                if (!fileJos.createNewFile()) {
                    throw new RuntimeException("couldn't create File");
                }
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileNameJos));
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mItemLock.unlock();
        }
        return true;
    }

    public boolean loadJos() {

        mItemLock.lock();
        try {
            File file = new File(fileNameJos);
            if (!file.exists())
                return false;
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileNameJos));
            MediaFileRepoList loadedList = (MediaFileRepoList) objectInputStream.readObject();
            this.repoList = loadedList.getRepoList();
            this.maxCapacity = loadedList.getMaxCapacity();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            mItemLock.unlock();
        }

        return true;
    }

    public void attachObserverToList(OberserverTyp observerTyp) {
        this.oberserverTypeEnumList.add(observerTyp);
        if (!repoList.isEmpty()) {
            for (MediaFileRepository repo : repoList) {
                if (observerTyp.equals(OberserverTyp.tag)) {
                    TagObserver tagObserver = new TagObserver(repo);
                    repo.attachObserver(tagObserver);
                } else if (observerTyp.equals(OberserverTyp.capacity)) {
                    CapacityObserver tagObserver = new CapacityObserver(repo);
                    repo.attachObserver(tagObserver);
                }
            }
        }
    }

    public void detachObserverFromList(OberserverTyp oberserverTyp) {
        this.oberserverTypeEnumList.remove(oberserverTyp);
    }

    private void attachCurrentObserversToNewInstance(int index) {
        if (!repoList.isEmpty()) {
            for (MediaFileRepository repo : repoList) {
                if (repo.getNumberOfRepository() == index) {
                    if (!oberserverTypeEnumList.isEmpty()) {
                        for (OberserverTyp oberserverTyp : oberserverTypeEnumList) {
                            if (oberserverTyp.equals(OberserverTyp.tag)) {
                                TagObserver tagObserver = new TagObserver(repo);
                                repo.attachObserver(tagObserver);
                            }
                            if (oberserverTyp.equals(OberserverTyp.capacity)) {
                                CapacityObserver capacityObserver = new CapacityObserver(repo);
                                repo.attachObserver(capacityObserver);
                            }
                        }
                    }
                }
            }
        }
    }
}