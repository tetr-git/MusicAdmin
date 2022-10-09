package domain_logic.files;

import domain_logic.enums.MediaTypes;
import domain_logic.enums.Tag;
import domain_logic.producer.UploaderImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AudioFileTest {

    UploaderImpl up1 = new UploaderImpl("Hans");
    Collection<Tag> tags =  new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News));
    BigDecimal bitrate = new BigDecimal("48.000");
    Duration length = Duration.ofSeconds(215);
    int samplingRate = 320;

    //Date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar today = Calendar.getInstance();
    Date todayAsDate = today.getTime();

    AudioFile audioFile = new AudioFile(up1, tags, bitrate, length, samplingRate);

    @Test
    void getSamplingRate() {
        assertEquals(samplingRate,audioFile.getSamplingRate());
    }

    @Test
    void typeString() {
        assertEquals(MediaTypes.AUDIO.toString(),audioFile.typeString());
    }

    @Test
    void testToString() {
        assertEquals("\tAudio\tHans\t[Lifestyle, News]\t0\t48.000\tPT3M35S\t10.320000\t"+sdf.format(todayAsDate)+"\t320",audioFile.toString());
    }
}