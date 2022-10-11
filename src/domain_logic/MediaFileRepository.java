package domain_logic;

import domain_logic.enums.Tag;
import domain_logic.files.MediaFile;
import domain_logic.producer.Uploader;
import domain_logic.producer.UploaderImpl;
import observer.Observable;
import observer.Observer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MediaFileRepository implements Serializable, Observable {

    static final long serialVersionUID = 1L;
    private final List<MediaFile> mediaFileList = new LinkedList<>();
    private final List<Uploader> uploaderList = new LinkedList<>();
    private final BigDecimal maxCapacity;
    private final Lock mItemLock = new ReentrantLock();

    public MediaFileRepository(BigDecimal maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public LinkedList<MediaFile> readMediaList() {
        return new LinkedList<>(mediaFileList);
    }

    public LinkedList<Uploader> readUploaderList() {
        return new LinkedList<>(uploaderList);
    }

    public BigDecimal getCurrentCapacity() {
        BigDecimal currentCapacity = new BigDecimal(0);
        for (MediaFile m : mediaFileList) {
            currentCapacity = currentCapacity.add(m.getSize());
        }
        return currentCapacity;
    }

    public BigDecimal getMaxCapacity() {
        return maxCapacity;
    }

    public boolean insertUploader(UploaderImpl addU) {
        mItemLock.lock();
        if (addU.getName().equalsIgnoreCase("")) {
            return false;
        }
        for (Uploader i : uploaderList) {
            if (i.getName().equalsIgnoreCase(addU.getName())) {
                return false;
            }
        }
        uploaderList.add(addU);
        mItemLock.unlock();
        return true;
    }

    public boolean insertUploaderFromString(String string) {
        return insertUploader(new UploaderImpl(string));
    }

    public boolean deleteUploader(String delU) {
        mItemLock.lock();
        try {
            for (Iterator<MediaFile> iterator = mediaFileList.iterator(); iterator.hasNext(); ) {
                MediaFile mediaElement = iterator.next();
                if (mediaElement.getUploader().getName().equalsIgnoreCase(delU)) {
                    iterator.remove();
                    updateAllMediaElementAddresses();
                    this.notifyObservers();
                }
            }
            return uploaderList.removeIf(uploader -> uploader.getName().equalsIgnoreCase(delU));
        } finally {
            mItemLock.unlock();
        }
    }

    private boolean capacityCheck(MediaFile m) {
        BigDecimal sum = getCurrentCapacity().add(m.getSize());
        //maxCapacity.compare returns 1 if it's bigger
        // than sum of current cap + m.size
        return maxCapacity.compareTo(sum) > 0;
    }

    public boolean insertMediaFile(MediaFile mediaFile) {
        mItemLock.lock();
        try {
            if (capacityCheck(mediaFile)) {
                for (Uploader i : uploaderList) {
                    if (i.getName().equalsIgnoreCase(mediaFile.getUploader().getName())) {
                        mediaFileList.add(mediaFile);
                        updateAllMediaElementAddresses();
                        this.notifyObservers();
                        return true;
                    }
                }
            }
            return false;
        } finally {
            mItemLock.unlock();
        }
    }

    /**
     * Delete media files.
     *
     * @author https://stackoverflow.com/questions/223918/iterating-through-a-collection-avoiding-concurrentmodificationexception-when-re#comment56183223_23908758
     */
    public boolean deleteMediaFiles(String address) {
        mItemLock.lock();
        boolean deleteSuccessful = false;
        try {
            for (Iterator<MediaFile> iterator = mediaFileList.iterator(); iterator.hasNext(); ) {
                MediaFile mediaFile = iterator.next();
                if (mediaFile.getAddress().equalsIgnoreCase(address)) {
                    iterator.remove();
                    this.notifyObservers();
                    deleteSuccessful = true;
                }
            }
        } finally {
            mItemLock.unlock();
        }
        updateAllMediaElementAddresses();
        return deleteSuccessful;
    }

    //todo last thing but why?
    private void updateAllMediaElementAddresses() {
        mItemLock.lock();
        int addressInt = 1;
        try {
            for (MediaFile m : mediaFileList) {
                m.setAddress(String.valueOf(addressInt));
                addressInt++;
            }
        } finally {
            mItemLock.unlock();

        }
    }

    public boolean updateAccessCounterMediaFile(String address) {
        for (MediaFile i : mediaFileList) {
            if (i.getAddress().equalsIgnoreCase(address)) {
                i.updateAccessCount();
                return true;
            }
        }
        return false;
    }

    public LinkedHashMap<Uploader, Integer> readUploaderWithCountedMediaElements() {
        LinkedHashMap<Uploader, Integer> map = new LinkedHashMap<>();
        //Loop to show existing uploader without any added mediaElements
        for (Uploader u : uploaderList) {
            map.put(u, 0);
        }
        for (Uploader u : uploaderList) {
            int counter = 0;
            for (MediaFile m : mediaFileList) {
                if (m.getUploader().getName().equals(u.getName())) {
                    map.replace(u, ++counter);
                }
            }
        }
        return map;
    }

    public ArrayList<Tag> listEnumTags() {
        //List<Tag> tagList = Arrays.asList(Tag.values());
        ArrayList<Tag> tagList = new ArrayList<>();
        for (MediaFile m : mediaFileList) {
            Collection<Tag> collection = m.getTags();
            ArrayList<Tag> arrayList = new ArrayList<>(collection);
            for (Tag tagOfMediaElement : arrayList) {
                if (!tagList.contains(tagOfMediaElement)) {
                    tagList.add(tagOfMediaElement);
                }
            }
        }
        return tagList;
    }


    //todo string mediaType

    public ArrayList<MediaFile> readFilteredMediaElementsByClass(String type) {
        ArrayList<MediaFile> list = new ArrayList<>();
        for (MediaFile m : mediaFileList) {
            if (m.typeString().equalsIgnoreCase(type)) {
                list.add(m);
            }
        }
        return list;
    }

    /**
     * @param mediaType for example "audio"
     * @return number of choosen Mediafile
     * todo replace string mediaType
     */
    public int NumberOfMediaType(String mediaType) {
        int counter = 0;
        for (MediaFile m : mediaFileList) {
            if (m.typeString().equalsIgnoreCase(mediaType)) {
                counter++;
            }
        }
        return counter;
    }

    //observer functionality

    private final List<observer.Observer> observerList = new LinkedList<observer.Observer>();

    public List<observer.Observer> getObserverList() {
        return observerList;
    }

    @Override
    public void attachObserver(observer.Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void detachObserver(observer.Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : this.observerList) {
            observer.update();
        }
    }

    public int getCurrentNumberOfMediaElements() {
        int counter = 0;
        for (MediaFile m : mediaFileList) {
            counter++;
        }
        return counter;
    }

    //RepoList Functionality

    private int numberOfRepository = 0;

    public int getNumberOfRepository() {
        return numberOfRepository;
    }

    public void setNumberOfRepository(int numberOfRepository) {
        this.numberOfRepository = numberOfRepository;
    }

    private boolean isActiveRepository = false;

    public boolean isActiveRepository() {
        return isActiveRepository;
    }

    public void setActiveRepository(boolean activeRepository) {
        isActiveRepository = activeRepository;
    }
}
