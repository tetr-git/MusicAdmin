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

class InteractiveVideoFileTest {

    UploaderImpl up1 = new UploaderImpl("Hans");

    Collection<Tag> tags =  new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News));
    BigDecimal bitrate = new BigDecimal("48.000");
    Duration length = Duration.ofSeconds(215);
    String address = "3";
    String type = "News";
    int resolution = 1080;

    InteractiveVideoFile mediaElement = new InteractiveVideoFile(up1,
            tags,
            bitrate,
            length,
            address,
            type,
            resolution);

    @Test
    void getType() {
        assertEquals(type,mediaElement.getType());
    }

    @Test
    void getResolution() {
        assertEquals(resolution,mediaElement.getResolution());
    }

    @Test
    void typeString() {
        assertEquals("InteractiveVideo",mediaElement.typeString());
    }
}