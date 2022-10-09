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

import static org.junit.jupiter.api.Assertions.*;

class SimObserverTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    //@source: https://www.baeldung.com/java-testing-system-out-println

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
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
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader);
        mediaFileRepository.insertMediaFile(mediaFile);

        SimObserver simObserver = new SimObserver(mediaFileRepository);
        mediaFileRepository.attachObserver(simObserver);

        mediaFileRepository.insertMediaFile(mediaFile2);

        assertEquals("MediaElement added", outputStreamCaptor.toString().trim());
    }

    @Test
    void testCaseMediaElementDeleted() {
        MediaFileRepository mediaFileRepository= new MediaFileRepository(new BigDecimal(1000000));
        String hans = "hans";
        UploaderImpl uploader = new UploaderImpl(hans);
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);

        mediaFileRepository.insertUploader(uploader);
        mediaFileRepository.insertMediaFile(mediaFile);

        SimObserver simObserver = new SimObserver(mediaFileRepository);
        mediaFileRepository.attachObserver(simObserver);

        mediaFileRepository.deleteMediaFiles("1");

        assertEquals("MediaElement deleted", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}