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

class CapacityObserverTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    //@source: https://www.baeldung.com/java-testing-system-out-println

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testCaseMediaElementAdded() {
        MediaFileRepository mediaFileRepository= new MediaFileRepository(new BigDecimal(11000));
        String hans = "hans";
        UploaderImpl uploader = new UploaderImpl(hans);
        MediaFile mediaFile = new AudioFile(uploader,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"), Duration.ofSeconds(215),320);
        mediaFileRepository.insertUploader(uploader);

        CapacityObserver capacityObserver = new CapacityObserver(mediaFileRepository);
        mediaFileRepository.register(capacityObserver);

        mediaFileRepository.insertMediaFile(mediaFile);

        assertEquals("Reached 90% of maxCapacity", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}