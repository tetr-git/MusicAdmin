package domain_logic.files;

import domain_logic.enums.Tag;
import domain_logic.producer.UploaderImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class LicensedAudioVideoFileTest {

    UploaderImpl up1 = new UploaderImpl("Hans");
    Collection<Tag> tags =  new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News));
    BigDecimal bitrate = new BigDecimal("48.000");
    Duration length = Duration.ofSeconds(215);
    int samplingRate = 320;
    String holder = "Universal";
    int resolution = 1080;

    LicensedAudioVideoFile mediaElement = new LicensedAudioVideoFile(up1,
            tags,
            bitrate,
            length,
            samplingRate,
            holder,
            resolution);

    @Test
    void getSamplingRate() {
        assertEquals(samplingRate,mediaElement.getSamplingRate());
    }

    @Test
    void getHolder() {
        assertEquals(holder,mediaElement.getHolder());
    }

    @Test
    void getResolution() {
        assertEquals(resolution,mediaElement.getResolution());
    }

    @Test
    void typeString() {
        assertEquals("LicensedAudioVideo",mediaElement.typeString());
    }
}