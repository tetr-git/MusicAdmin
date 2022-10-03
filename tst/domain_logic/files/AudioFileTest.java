package domain_logic.files;

import domain_logic.enums.MediaTypes;
import domain_logic.enums.Tag;
import domain_logic.producer.UploaderImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class AudioFileTest {

    UploaderImpl up1 = new UploaderImpl("Hans");
    Collection<Tag> tags =  new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News));
    BigDecimal bitrate = new BigDecimal("48.000");
    Duration length = Duration.ofSeconds(215);
    int samplingRate = 320;

    AudioFile audioFile = new AudioFile(up1, tags, bitrate, length, samplingRate);

    @Test
    void getSamplingRate() {
        assertEquals(samplingRate,audioFile.getSamplingRate());
    }

    @Test
    void typeString() {
        assertEquals(MediaTypes.AUDIO.toString(),audioFile.typeString());
    }
}