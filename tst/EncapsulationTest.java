import domain_logic.MediaFileRepoList;
import domain_logic.MediaFileRepository;
import domain_logic.enums.Tag;
import domain_logic.files.AudioFile;
import domain_logic.files.AudioVideoFile;
import domain_logic.files.MediaFile;
import domain_logic.producer.Uploader;
import domain_logic.producer.UploaderImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class EncapsulationTest {
    @Test
    void encapsulationExampleMediaList() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000));
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

        LinkedList<MediaFile> list = mediaFileRepository.readMediaList();
        list.clear();
        list = mediaFileRepository.readMediaList();

        assertFalse(list.isEmpty());
    }

    @Test
    void encapsulationExampleUploaderList() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000));
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

        LinkedList<Uploader> list = mediaFileRepository.readUploaderList();
        list.clear();
        list = mediaFileRepository.readUploaderList();

        assertFalse(list.isEmpty());
    }

    @Test
    void encapsulationExampleTagList() {
        MediaFileRepository mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000));
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

        ArrayList<Tag> list = mediaFileRepository.listEnumTags();
        list.clear();
        list = mediaFileRepository.listEnumTags();

        assertFalse(list.isEmpty());
    }

    @Test
    void testRepoInstances() {
        MediaFileRepoList mediaFileRepoList = new MediaFileRepoList(new BigDecimal(10000000));

        mediaFileRepoList.changeStateAllRepositories(new String[]{"storage", "1", "2", "0"});
        LinkedList<MediaFileRepository> list = mediaFileRepoList.getRepoList();
        list.clear();
        list = mediaFileRepoList.getRepoList();

        assertFalse(list.isEmpty());
    }
}
