package domain_logic;

import domain_logic.enums.Tag;
import domain_logic.files.AudioFile;
import domain_logic.files.AudioVideoFile;
import domain_logic.files.LicensedAudioFile;
import domain_logic.files.MediaFile;
import domain_logic.producer.Uploader;
import domain_logic.producer.UploaderImpl;
import observer.CapacityObserver;
import observer.SimObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MediaFileRepositoryTest {

    MediaFileRepository mediaFileRepository;

    @BeforeEach
    void setUp() {
        mediaFileRepository = new MediaFileRepository(new BigDecimal(100000));
    }

    @Test
    void readMediaList() {
        assertEquals(0, mediaFileRepository.readMediaList().size());
    }

    @Test
    void readUploaderList() {
        assertEquals(0, mediaFileRepository.readUploaderList().size());
    }

    @Test
    void getCurrentCapacity() {
        assertEquals(new BigDecimal(0), mediaFileRepository.getCurrentCapacity());
    }

    @Test
    void getCurrentCapacityFilled() {
        assertEquals(new BigDecimal(0), mediaFileRepository.getCurrentCapacity());
    }

    @Test
    void getMaxCapacity() {
        assertEquals(new BigDecimal(100000), mediaFileRepository.getMaxCapacity());
    }

    @Test
    void insertUploader() {
        UploaderImpl uploader = new UploaderImpl("Hans");

        assertTrue(mediaFileRepository.insertUploader(uploader));
    }

    @Test
    void insertExistingUploader(){
        UploaderImpl uploader1 = new UploaderImpl("Hans");

        mediaFileRepository.insertUploader(uploader1);

        assertFalse(mediaFileRepository.insertUploader(uploader1));
    }

    @Test
    void insertUploaderWithSameName() {
        UploaderImpl uploader1 = new UploaderImpl("Hans");
        UploaderImpl uploader2 = new UploaderImpl("Hans");

        mediaFileRepository.insertUploader(uploader1);

        assertFalse(mediaFileRepository.insertUploader(uploader2));
    }

    @Test
    void insertUploaderFromString() {
        String u = "Hans";

        mediaFileRepository.insertUploaderFromString(u);
        mediaFileRepository.readUploaderList();

        assertEquals(u, mediaFileRepository.readUploaderList().getFirst().getName());
    }

    @Test
    void insertUploaderFromStringCheckReturnValue() {
        String u = "Hans";

        assertTrue(mediaFileRepository.insertUploaderFromString(u));
    }

    @Test
    void deleteUploader() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        UploaderImpl up2 = new UploaderImpl("Peter");

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertUploader(up2);

        assertTrue(mediaFileRepository.deleteUploader("Hans"));
    }

    @Test
    void deleteNotExistingUploader() {
        UploaderImpl up1 = new UploaderImpl("Hans");

        mediaFileRepository.insertUploader(up1);

        assertFalse(mediaFileRepository.deleteUploader("Bert"));
    }

    @Test
    void deleteUploaderWithMediaElement() {
        String hans = "hans";
        UploaderImpl uploader = new UploaderImpl(hans);
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader);
        mediaFileRepository.insertMediaFile(mediaFile);
        mediaFileRepository.deleteUploader(hans);

        assertEquals(0, mediaFileRepository.readMediaList().size());
    }

    @Test
    void insertMediaFile() {
        UploaderImpl uploader = new UploaderImpl("Hans");
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader);

        assertTrue(mediaFileRepository.insertMediaFile(mediaFile));
    }

    @Test
    void insertMediaFileIntoFullStorage() {
        UploaderImpl uploader = new UploaderImpl("Hans");
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(21500),320);

        mediaFileRepository.insertUploader(uploader);

        assertFalse(mediaFileRepository.insertMediaFile(mediaFile));
    }

    @Test
    void insertMediaFileWithoutUploader() {
        UploaderImpl uploader = new UploaderImpl("Hans");
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);

        assertFalse(mediaFileRepository.insertMediaFile(mediaFile));
    }

    @Test
    void readListWithTwoElements() {

        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"),
                Duration.ofSeconds(215),
                320);
        MediaFile audioVideoImpl = new AudioVideoFile(up1,
                new ArrayList<>(Collections.singletonList(Tag.Lifestyle)),
                new BigDecimal("48000"),
                Duration.ofSeconds(300),
                320,
                1920);

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioImpl);
        mediaFileRepository.insertMediaFile(audioVideoImpl);

        assertEquals(2, mediaFileRepository.readMediaList().size());
    }

    @Test
    void checkUpdateAddressMethod() {
        UploaderImpl uploader = new UploaderImpl("Hans");
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader);
        mediaFileRepository.insertMediaFile(mediaFile);

        assertEquals("1",mediaFile.getAddress());
    }

    @Test
    void checkUpdatedAddressMethodAfterDeletingMediaFile() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        UploaderImpl uploader2 = new UploaderImpl("peter");

        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);
        MediaFile mediaFile2 = new AudioFile(uploader2,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader1);
        mediaFileRepository.insertUploader(uploader2);
        mediaFileRepository.insertMediaFile(mediaFile1);
        mediaFileRepository.insertMediaFile(mediaFile2);
        mediaFileRepository.deleteUploader("hans");

        assertEquals("1",mediaFile2.getAddress());
    }

    @Test
    void deleteMediaFiles() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        UploaderImpl uploader2 = new UploaderImpl("peter");

        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);
        MediaFile mediaFile2 = new AudioFile(uploader2,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader1);
        mediaFileRepository.insertUploader(uploader2);
        mediaFileRepository.insertMediaFile(mediaFile1);
        mediaFileRepository.insertMediaFile(mediaFile2);
        mediaFileRepository.deleteMediaFiles("1");

        assertEquals(1, mediaFileRepository.readMediaList().size());
    }

    @Test
    void deleteSingleMediaFiles() {
        UploaderImpl uploader1 = new UploaderImpl("hans");

        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader1);
        mediaFileRepository.insertMediaFile(mediaFile1);

        assertTrue(mediaFileRepository.deleteMediaFiles("1"));
    }

    @Test
    void deleteSingleMediaFileCheckUpdatedAddress() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        UploaderImpl uploader2 = new UploaderImpl("peter");

        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);
        MediaFile mediaFile2 = new AudioFile(uploader2,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader1);
        mediaFileRepository.insertUploader(uploader2);
        mediaFileRepository.insertMediaFile(mediaFile1);
        mediaFileRepository.insertMediaFile(mediaFile2);
        mediaFileRepository.deleteMediaFiles("1");

        assertEquals("1",mediaFile2.getAddress());
    }

    @Test
    void updateAccessCounterMediaFile() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader1);
        mediaFileRepository.insertMediaFile(mediaFile1);
        mediaFileRepository.updateAccessCounterMediaFile("1");

        assertEquals(1,mediaFile1.getAccessCount());
    }

    @Test
    void updateAccessCounterMediaFileCheckReturnValue() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader1);
        mediaFileRepository.insertMediaFile(mediaFile1);

        assertTrue(mediaFileRepository.updateAccessCounterMediaFile("1"));
    }

    @Test
    void updateAccessCounterCantFindItem() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader1);
        mediaFileRepository.insertMediaFile(mediaFile1);

        assertFalse(mediaFileRepository.updateAccessCounterMediaFile("2"));
    }


    @Test
    void readUploaderWithCountedMediaElements() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        UploaderImpl up2 = new UploaderImpl("Bert");
        MediaFile audioFile = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"),
                Duration.ofSeconds(215),
                320);
        MediaFile audioVideoFile = new AudioVideoFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.Animal)),
                new BigDecimal("48000"),
                Duration.ofSeconds(300),
                320,
                1920);
        MediaFile licensedAudioFile = new LicensedAudioFile(up1,
                Collections.singleton(Tag.Lifestyle),
                new BigDecimal("48000"),
                Duration.ofSeconds(600),
                999,
                "Sony");

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertUploader(up2);
        mediaFileRepository.insertMediaFile(audioFile);
        mediaFileRepository.insertMediaFile(audioVideoFile);
        mediaFileRepository.insertMediaFile(licensedAudioFile);

        HashMap<Uploader, Integer> hashMap = mediaFileRepository.readUploaderWithCountedMediaElements();

        assertEquals(3, hashMap.get(up1));
    }

    @Test
    void readUploaderWithCountedMediaElementsEmptyList() {
        UploaderImpl up1 = new UploaderImpl("Hans");

        mediaFileRepository.insertUploader(up1);
        HashMap<Uploader, Integer> hashMap;
        hashMap = mediaFileRepository.readUploaderWithCountedMediaElements();

        assertEquals(0, hashMap.get(up1));
    }

    @Test
    void listEnumTags() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"),
                Duration.ofSeconds(215),
                320);

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioImpl);
        List<Tag> tagList = new ArrayList<>();

        tagList.add(Tag.Lifestyle);
        tagList.add(Tag.News);

        assertEquals(tagList, mediaFileRepository.listEnumTags());
    }

    @Test
    void readFilteredMediaElementsByClass() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"),
                Duration.ofSeconds(215),
                320);

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioImpl);

        ArrayList<MediaFile> list = mediaFileRepository.readFilteredMediaElementsByClass("audio");

        assertEquals("Audio", mediaFileRepository.readFilteredMediaElementsByClass("audio").get(0).typeString());
    }




    @Test
    void numberOfMediaType() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"),
                Duration.ofSeconds(215),
                320);

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioImpl);

        assertEquals(1, mediaFileRepository.NumberOfMediaType("audio"));
    }

    @Test
    void getObserverList() {
        CapacityObserver capacityObserver = new CapacityObserver(mediaFileRepository);

        mediaFileRepository.register(capacityObserver);

        assertEquals(1,mediaFileRepository.getObserverList().size());
    }

    @Test
    void register() {
        CapacityObserver capacityObserver = new CapacityObserver(mediaFileRepository);
        SimObserver simObserver = new SimObserver(mediaFileRepository);

        mediaFileRepository.register(capacityObserver);
        mediaFileRepository.register(simObserver);

        assertEquals(2,mediaFileRepository.getObserverList().size());
    }

    @Test
    void deregister() {
        CapacityObserver capacityObserver = new CapacityObserver(mediaFileRepository);
        SimObserver simObserver = new SimObserver(mediaFileRepository);

        mediaFileRepository.register(capacityObserver);
        mediaFileRepository.register(simObserver);

        mediaFileRepository.deregister(capacityObserver);

        assertEquals(1,mediaFileRepository.getObserverList().size());
    }

    @Test
    void notifyObservers() {
        CapacityObserver capacityObserver = mock(CapacityObserver.class);

        mediaFileRepository.register(capacityObserver);

        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"),
                Duration.ofSeconds(215),
                320);
        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioImpl);

        verify(capacityObserver).update();
    }

    @Test
    void getCurrentNumberOfMediaElements() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48000"),
                Duration.ofSeconds(215),
                320);
        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioImpl);

        assertEquals(1,mediaFileRepository.getCurrentNumberOfMediaElements());
    }

    @Test
    void getCurrentNumberOfMediaElementsWIthEmptyRepository() {
        assertEquals(0,mediaFileRepository.getCurrentNumberOfMediaElements());
    }


}
