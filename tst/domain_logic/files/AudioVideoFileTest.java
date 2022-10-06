package domain_logic.files;

import domain_logic.enums.Tag;
import domain_logic.producer.UploaderImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AudioVideoFileTest {

    UploaderImpl up1 = new UploaderImpl("Hans");
    Collection<Tag> tags =  new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News));
    BigDecimal bitrate = new BigDecimal("48.000");
    Duration length = Duration.ofSeconds(215);
    int samplingRate = 320;
    int resolution = 1080;

    //Date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar today = Calendar.getInstance();
    Date todayAsDate = today.getTime();

    AudioVideoFile mediaElement = new AudioVideoFile(up1,
            tags,
            bitrate,
            length,
            samplingRate,
            resolution);

    @Test
    void getSamplingRate() {
        assertEquals(samplingRate, mediaElement.getSamplingRate());
    }

    @Test
    void getResolution() {
        assertEquals(resolution, mediaElement.getResolution());
    }

    @Test
    void typeString() {
        assertEquals("AudioVideo",mediaElement.typeString());
    }

    @Test
    void testToString() {
        assertEquals("\tAudioVideo\tHans\t[Lifestyle, News]\t0\t48.000\tPT3M35S\t10320.000\t"+sdf.format(todayAsDate)+"\t320\t1080",
                mediaElement.toString());
    }
}