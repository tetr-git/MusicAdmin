package domain_logic;

import domain_logic.enums.Tag;
import domain_logic.files.AudioFile;
import domain_logic.files.AudioVideoFile;
import domain_logic.files.LicensedAudioFile;
import domain_logic.files.MediaFile;
import domain_logic.producer.Uploader;
import domain_logic.producer.UploaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MediaFileRepositoryTest {

    MediaFileRepository mnf;

    @BeforeEach
    void setUp() {
        mnf = new MediaFileRepository(new BigDecimal(100000));
    }

    @Test
    void readMediaList() {
        assertEquals(0, mnf.readMediaList().size());

    }

    @Test
    void readUploaderList() {
        assertEquals(0, mnf.readUploaderList().size());
    }

    @Test
    void getCurrentCapacity() {
        assertEquals(new BigDecimal(0), mnf.getCurrentCapacity());
    }

    @Test
    void getCurrentCapacityFilled() {
        assertEquals(new BigDecimal(0), mnf.getCurrentCapacity());
    }

    @Test
    void getMaxCapacity() {
        assertEquals(new BigDecimal(100000), mnf.getMaxCapacity());
    }

    @Test
    void insertUploader() {
        UploaderImpl uploader = new UploaderImpl("Hans");

        assertTrue(mnf.insertUploader(uploader));
    }

    @Test
    void insertExistingUploader(){
        UploaderImpl uploader1 = new UploaderImpl("Hans");

        mnf.insertUploader(uploader1);

        assertFalse(mnf.insertUploader(uploader1));
    }

    @Test
    void insertUploaderWithSameName() {
        UploaderImpl uploader1 = new UploaderImpl("Hans");
        UploaderImpl uploader2 = new UploaderImpl("Hans");

        mnf.insertUploader(uploader1);

        assertFalse(mnf.insertUploader(uploader2));
    }

    @Test
    void insertUploaderFromString() {
        String u = "Hans";

        mnf.insertUploaderFromString(u);
        mnf.readUploaderList();

        assertEquals(u, mnf.readUploaderList().getFirst().getName());
    }

    @Test
    void insertUploaderFromStringCheckReturnValue() {
        String u = "Hans";

        assertTrue(mnf.insertUploaderFromString(u));
    }

    @Test
    void deleteUploader() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        UploaderImpl up2 = new UploaderImpl("Peter");

        mnf.insertUploader(up1);
        mnf.insertUploader(up2);

        assertTrue(mnf.deleteUploader("Hans"));
    }

    @Test
    void deleteNotExistingUploader() {
        UploaderImpl up1 = new UploaderImpl("Hans");

        mnf.insertUploader(up1);

        assertFalse(mnf.deleteUploader("Bert"));
    }

    @Test
    void deleteUploaderWithMediaElement() {
        String hans = "hans";
        UploaderImpl uploader = new UploaderImpl(hans);
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mnf.insertUploader(uploader);
        mnf.insertMediaFile(mediaFile);
        mnf.deleteUploader(hans);

        assertEquals(0,mnf.readMediaList().size());
    }

    @Test
    void insertMediaFile() {
        UploaderImpl uploader = new UploaderImpl("Hans");
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mnf.insertUploader(uploader);

        assertTrue(mnf.insertMediaFile(mediaFile));
    }

    @Test
    void insertMediaFileIntoFullStorage() {
        UploaderImpl uploader = new UploaderImpl("Hans");
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.00000"), Duration.ofSeconds(215009),320);

        mnf.insertUploader(uploader);

        assertFalse(mnf.insertMediaFile(mediaFile));
    }

    @Test
    void insertMediaFileWithoutUploader() {
        UploaderImpl uploader = new UploaderImpl("Hans");
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        assertFalse(mnf.insertMediaFile(mediaFile));
    }

    @Test
    void readListWithTwoElements() {

        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(215),
                320);
        MediaFile audioVideoImpl = new AudioVideoFile(up1,
                new ArrayList<>(Collections.singletonList(Tag.Lifestyle)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(300),
                320,
                1920);

        mnf.insertUploader(up1);
        mnf.insertMediaFile(audioImpl);
        mnf.insertMediaFile(audioVideoImpl);

        assertEquals(2, mnf.readMediaList().size());
    }

    @Test
    void checkUpdateAddressMethod() {
        UploaderImpl uploader = new UploaderImpl("Hans");
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mnf.insertUploader(uploader);
        mnf.insertMediaFile(mediaFile);

        assertEquals("1",mediaFile.getAddress());
    }

    @Test
    void checkUpdatedAddressMethodAfterDeletingMediaFile() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        UploaderImpl uploader2 = new UploaderImpl("peter");

        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);
        MediaFile mediaFile2 = new AudioFile(uploader2,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mnf.insertUploader(uploader1);
        mnf.insertUploader(uploader2);
        mnf.insertMediaFile(mediaFile1);
        mnf.insertMediaFile(mediaFile2);
        mnf.deleteUploader("hans");

        assertEquals("1",mediaFile2.getAddress());
    }

    @Test
    void deleteMediaFiles() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        UploaderImpl uploader2 = new UploaderImpl("peter");

        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);
        MediaFile mediaFile2 = new AudioFile(uploader2,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mnf.insertUploader(uploader1);
        mnf.insertUploader(uploader2);
        mnf.insertMediaFile(mediaFile1);
        mnf.insertMediaFile(mediaFile2);
        mnf.deleteMediaFiles("1");

        assertEquals(1,mnf.readMediaList().size());
    }

    @Test
    void deleteSingleMediaFiles() {
        UploaderImpl uploader1 = new UploaderImpl("hans");

        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mnf.insertUploader(uploader1);
        mnf.insertMediaFile(mediaFile1);

        assertTrue(mnf.deleteMediaFiles("1"));
    }

    @Test
    void deleteSingleMediaFileCheckUpdatedAddress() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        UploaderImpl uploader2 = new UploaderImpl("peter");

        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);
        MediaFile mediaFile2 = new AudioFile(uploader2,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mnf.insertUploader(uploader1);
        mnf.insertUploader(uploader2);
        mnf.insertMediaFile(mediaFile1);
        mnf.insertMediaFile(mediaFile2);
        mnf.deleteMediaFiles("1");

        assertEquals("1",mediaFile2.getAddress());
    }

    @Test
    void updateAccessCounterMediaFile() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mnf.insertUploader(uploader1);
        mnf.insertMediaFile(mediaFile1);
        mnf.updateAccessCounterMediaFile("1");

        assertEquals(1,mediaFile1.getAccessCount());
    }

    @Test
    void updateAccessCounterMediaFileCheckReturnValue() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mnf.insertUploader(uploader1);
        mnf.insertMediaFile(mediaFile1);

        assertTrue(mnf.updateAccessCounterMediaFile("1"));
    }

    @Test
    void updateAccessCounterCantFindItem() {
        UploaderImpl uploader1 = new UploaderImpl("hans");
        MediaFile mediaFile1 = new AudioFile(uploader1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mnf.insertUploader(uploader1);
        mnf.insertMediaFile(mediaFile1);

        assertFalse(mnf.updateAccessCounterMediaFile("2"));
    }


    @Test
    void readUploaderWithCountedMediaElements() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        UploaderImpl up2 = new UploaderImpl("Bert");
        MediaFile audioFile = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(215),
                320);
        MediaFile audioVideoFile = new AudioVideoFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.Animal)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(300),
                320,
                1920);
        MediaFile licensedAudioFile = new LicensedAudioFile(up1,
                Collections.singleton(Tag.Lifestyle),
                new BigDecimal("48.000"),
                Duration.ofSeconds(600),
                999,
                "Sony");

        mnf.insertUploader(up1);
        mnf.insertUploader(up2);
        mnf.insertMediaFile(audioFile);
        mnf.insertMediaFile(audioVideoFile);
        mnf.insertMediaFile(licensedAudioFile);

        HashMap<Uploader, Integer> hashMap = mnf.readUploaderWithCountedMediaElements();

        assertEquals(3, hashMap.get(up1));
    }

    @Test
    void readUploaderWithCountedMediaElementsEmptyList() {
        UploaderImpl up1 = new UploaderImpl("Hans");

        mnf.insertUploader(up1);
        HashMap<Uploader, Integer> hashMap;
        hashMap = mnf.readUploaderWithCountedMediaElements();

        assertEquals(0, hashMap.get(up1));
    }

    @Test
    void listEnumTags() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(215),
                320);

        mnf.insertUploader(up1);
        mnf.insertMediaFile(audioImpl);
        List<Tag> tagList = new ArrayList<>();

        tagList.add(Tag.Lifestyle);
        tagList.add(Tag.News);

        assertEquals(tagList, mnf.listEnumTags());
    }

    @Test
    void readFilteredMediaElementsByClass() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(215),
                320);

        mnf.insertUploader(up1);
        mnf.insertMediaFile(audioImpl);

        ArrayList<MediaFile> list = mnf.readFilteredMediaElementsByClass("audio");

        assertEquals("Audio",mnf.readFilteredMediaElementsByClass("audio").get(0).typeString());
    }




    @Test
    void numberOfMediaType() {
        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(215),
                320);

        mnf.insertUploader(up1);
        mnf.insertMediaFile(audioImpl);

        assertEquals(1,mnf.NumberOfMediaType("audio"));
    }

}
