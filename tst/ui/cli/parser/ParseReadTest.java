package ui.cli.parser;

import domain_logic.MediaFileRepository;
import domain_logic.enums.Tag;
import domain_logic.files.AudioFile;
import domain_logic.files.AudioVideoFile;
import domain_logic.files.LicensedAudioFile;
import domain_logic.files.MediaFile;
import domain_logic.producer.UploaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routing.handler.EventHandler;
import routing.listener.*;
import ui.cli.ConsoleManagement;
import ui.cli.parser.ParseRead;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static org.mockito.Mockito.*;

class ParseReadTest {

    MediaFileRepository mediaFileRepository;
    EventHandler inputHandler;
    EventHandler outputHandler;
    ConsoleManagement consoleManagement;
    SimpleDateFormat sdf;
    Date todayAsDate;

    @BeforeEach
    void setUp() {
        mediaFileRepository = new MediaFileRepository(new BigDecimal(1000000000));
        inputHandler = new EventHandler();
        outputHandler = new EventHandler();
        inputHandler.add(new ReadMediaListener(mediaFileRepository,outputHandler));
        inputHandler.add(new ReadUploaderListener(mediaFileRepository,outputHandler));
        inputHandler.add(new ReadTagListener(mediaFileRepository,outputHandler));
        consoleManagement = mock(ConsoleManagement.class);
        outputHandler.add(new CliOutputListener(consoleManagement));

        //get date from today
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();
        todayAsDate = today.getTime();

    }

    @Test
    void ReadUploader() {

        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(215),
                320);

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioImpl);

        ParseRead parseRead = new ParseRead(inputHandler);

        parseRead.execute("uploader");
        verify(consoleManagement).writeToConsole("Hans\t1\n");
    }

    @Test
    void ReadMedia() {

        ParseRead parseRead = new ParseRead(inputHandler);

        UploaderImpl up1 = new UploaderImpl("Hans");
        MediaFile audioImpl = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(215),
                320);

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioImpl);

        parseRead.execute("content");

        verify(consoleManagement).writeToConsole("1\tAudio\tHans\t[Lifestyle, News]\t0\t48.000\tPT3M35S\t10320.000\t"+sdf.format(todayAsDate)+"\t320\n");
    }

    @Test
    void ReadMediaFilteredByType() {

        UploaderImpl up1 = new UploaderImpl("Hans");
        UploaderImpl up2 = new UploaderImpl("Bert");
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
        MediaFile audioFile = new AudioFile(up1,
                new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News)),
                new BigDecimal("48.000"),
                Duration.ofSeconds(215),
                320);

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertUploader(up2);
        mediaFileRepository.insertMediaFile(audioVideoFile);
        mediaFileRepository.insertMediaFile(licensedAudioFile);
        mediaFileRepository.insertMediaFile(audioFile);

        ParseRead parseRead = new ParseRead(inputHandler);

        parseRead.execute("content audio");
        verify(consoleManagement).writeToConsole("3\tAudio\tHans\t[Lifestyle, News]\t0\t48.000\tPT3M35S\t10320.000\t"+sdf.format(todayAsDate)+"\t320\n");
    }

    @Test
    void ReadExistingTags() {

        UploaderImpl up1 = new UploaderImpl("Hans");
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

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioVideoFile);
        mediaFileRepository.insertMediaFile(licensedAudioFile);
        ParseRead parseRead = new ParseRead(inputHandler);

        parseRead.execute("tag i");

        verify(consoleManagement).writeToConsole("Animal\tLifestyle\t");
    }

    @Test
    void ReadNotExistingTags() {

        UploaderImpl up1 = new UploaderImpl("Hans");
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

        mediaFileRepository.insertUploader(up1);
        mediaFileRepository.insertMediaFile(audioVideoFile);
        mediaFileRepository.insertMediaFile(licensedAudioFile);

        ParseRead parseRead = new ParseRead(inputHandler);
        parseRead.execute("tag e");

        verify(consoleManagement).writeToConsole("Tutorial\tNews\t");
    }
}