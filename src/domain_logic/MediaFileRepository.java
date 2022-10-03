package domain_logic;

import domain_logic.enums.Tag;
import domain_logic.files.MediaFile;
import domain_logic.producer.Uploader;
import domain_logic.producer.UploaderImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MediaFileRepository implements Serializable {

    static final long serialVersionUID = 1L;
    private List<MediaFile> mediaFileList = new LinkedList<>();
    private List<Uploader> uploaderList = new LinkedList<>();
    private final BigDecimal maxCapacity;
    private final Lock mItemLock = new ReentrantLock();

    public MediaFileRepository(BigDecimal maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public LinkedList <MediaFile> readMediaList() {
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
                    //todo this.notifyObservers();
                }
            }
            return uploaderList.removeIf(uploader -> uploader.getName().equalsIgnoreCase(delU));
        }
        finally {
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
                        //todo this.notifyObservers();
                        return true;
                    }
                }
            }
            return false;
        }
        finally {
            mItemLock.unlock();
        }
    }

    public boolean deleteMediaFiles(String address) {
        mItemLock.lock();
        boolean deleteSuccessful = false;
        try {
            for (Iterator<MediaFile> iterator = mediaFileList.iterator(); iterator.hasNext(); ) {
                MediaFile mediaFile = iterator.next();
                if (mediaFile.getAddress().equalsIgnoreCase(address)) {
                    iterator.remove();
                    //todo this.notifyObservers();
                    deleteSuccessful = true;
                }
            }
        }
        finally {
            mItemLock.unlock();
        }
        updateAllMediaElementAddresses();
        return deleteSuccessful;
    }

    private void updateAllMediaElementAddresses () {
        mItemLock.lock();
        int addressInt = 1;
        try {
            for (MediaFile m : mediaFileList) {
               m.setAddress(String.valueOf(addressInt));
               addressInt++;
            }
        }
        finally {
            mItemLock.unlock();
        }
    }

    public boolean updateAccessCounterMediaFile(String address) {
        boolean updateSuccessful = false;
        for (MediaFile i : mediaFileList) {
            if (i.getAddress().equalsIgnoreCase(address)) {
                i.updateAccessCount();
                updateSuccessful = true;
            }
        }
        return updateSuccessful;
    }

    /**
     * Read uploader with counted media elements hash map.
     *
     * @return hashMap with Uploader and Number of owned uploaded MediaElements
     */
    public HashMap<Uploader, Integer> readUploaderWithCountedMediaElements() {
        HashMap<Uploader, Integer> hashMap = new HashMap<>();
        //Loop to show existing uploader without any added mediaElements
        for (Uploader u : uploaderList) {
            hashMap.put(u, 0);
        }
        for (Uploader u : uploaderList) {
            int counter = 0;
            for (MediaFile m : mediaFileList) {
                if (m.getUploader().equals(u)) {
                    hashMap.replace(u, ++counter);
                }
            }
        }
        return hashMap;
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

    /**
     * Read filtered media elements by class hash map.
     *
     * @param type int
     * @return hashmap
     * todo string mediaType
     */
    public HashMap<String, MediaFile> readFilteredMediaElementsByClass(String type) {
        HashMap<String, MediaFile> hashMap = new HashMap<>();
        for (MediaFile m : mediaFileList) {
            if (m.typeString().equalsIgnoreCase(type)) {
                hashMap.put(m.typeString(), m);
            }
        }
        return hashMap;
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
}
