package observer;

import domain_logic.MediaFileRepository;
import domain_logic.enums.Tag;
import domain_logic.files.AudioFile;
import domain_logic.files.MediaFile;
import domain_logic.producer.UploaderImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TagObserverTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    //@source: https://www.baeldung.com/java-testing-system-out-println

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testCaseMediaElementAddedToEmptyList() {
        MediaFileRepository mediaFileRepository= new MediaFileRepository(new BigDecimal(11000));
        String hans = "hans";
        UploaderImpl uploader = new UploaderImpl(hans);
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);
        mediaFileRepository.insertUploader(uploader);

        TagObserver tagObserver = new TagObserver(mediaFileRepository);
        mediaFileRepository.register(tagObserver);

        mediaFileRepository.insertMediaFile(mediaFile);

        assertEquals("Tag Lifestyle added\n" +
                "Tag News added", outputStreamCaptor.toString().trim());
    }

    @Test
    void testCaseMediaElementAdded() {
        MediaFileRepository mediaFileRepository= new MediaFileRepository(new BigDecimal(1000000));
        String hans = "hans";
        UploaderImpl uploader = new UploaderImpl(hans);
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);
        MediaFile mediaFile2 = new AudioFile(uploader,
                new ArrayList<>(Collections.singletonList(Tag.Animal)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);
        mediaFileRepository.insertUploader(uploader);
        mediaFileRepository.insertMediaFile(mediaFile);

        TagObserver tagObserver = new TagObserver(mediaFileRepository);
        mediaFileRepository.register(tagObserver);

        mediaFileRepository.insertMediaFile(mediaFile2);

        assertEquals("Tag Animal added", outputStreamCaptor.toString().trim());
    }

    @Test
    void testCaseMediaElementDeleted() {
        MediaFileRepository mediaFileRepository= new MediaFileRepository(new BigDecimal(1000000));
        String hans = "hans";
        UploaderImpl uploader = new UploaderImpl(hans);
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);
        MediaFile mediaFile2 = new AudioFile(uploader,
                new ArrayList<>(Collections.singletonList(Tag.Animal)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);
        mediaFileRepository.insertUploader(uploader);
        mediaFileRepository.insertMediaFile(mediaFile);
        mediaFileRepository.insertMediaFile(mediaFile2);
        TagObserver tagObserver = new TagObserver(mediaFileRepository);
        mediaFileRepository.register(tagObserver);

        mediaFileRepository.deleteMediaFiles("2");

        assertEquals("Tag Animal removed", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}