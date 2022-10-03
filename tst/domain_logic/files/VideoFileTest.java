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

class VideoFileTest {

    UploaderImpl up1 = new UploaderImpl("Hans");
    Collection<Tag> tags =  new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News));
    BigDecimal bitrate = new BigDecimal("48.000");
    Duration length = Duration.ofSeconds(215);
    String address = "C://media/audio";
    int resolution = 320;

    VideoFile mediaElement = new VideoFile(up1,
            tags,
            bitrate,
            length,
            address,
            resolution);

    @Test
    void getResolution() {
        assertEquals(resolution,mediaElement.getResolution());
    }

    @Test
    void typeString() {
        assertEquals("Video",mediaElement.typeString());
    }
}