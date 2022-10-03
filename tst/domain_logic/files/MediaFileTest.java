package domain_logic.files;

import domain_logic.enums.Tag;
import domain_logic.producer.UploaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MediaFileTest {
    UploaderImpl up1 = new UploaderImpl("Hans");
    Collection<Tag> tags =  new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News));
    BigDecimal bitrate = new BigDecimal("48.000");
    Duration length = Duration.ofSeconds(215);
    String address = "";
    int samplingRate = 320;

    MediaFile mediaFile;

    @BeforeEach
    void setUp() {
        mediaFile = new AudioFile(up1, tags, bitrate, length, address, samplingRate);
    }

    @Test
    void getAddress() {
        assertEquals(address, mediaFile.getAddress());
    }

    @Test
    void getTags() {
        assertEquals(tags, mediaFile.getTags());
    }

    @Test
    void getAccessCount() {
        assertEquals(0, mediaFile.getAccessCount());
    }

    @Test
    void getBitrate() {
        assertEquals(bitrate, mediaFile.getBitrate());
    }

    @Test
    void getLength() {
        assertEquals(length, mediaFile.getLength());
    }

    @Test
    void getSize() {
        assertEquals(bitrate.multiply(new BigDecimal(length.getSeconds())), mediaFile.getSize());
    }

    @Test
    void getUploader() {
        assertEquals(up1, mediaFile.getUploader());
    }

    @Test
    void getUploadDate() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE,0);
        today.set(Calendar.SECOND,0);

        assertEquals(today.getTime().toString(), mediaFile.getUploadDate().toString());
    }

    @Test
    void updateAccessCount() {
        long currentAccessCounter = mediaFile.getAccessCount();

        mediaFile.updateAccessCount();

        assertEquals((currentAccessCounter+1), mediaFile.getAccessCount());
    }

    @Test
    void typeString() {
        assertEquals("Audio", mediaFile.typeString());
    }

    @Test
    void setAddress() {
        String string = "newAddress";

        mediaFile.setAddress(string);

        assertEquals("newAddress",mediaFile.getAddress());
    }
}