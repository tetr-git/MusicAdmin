package domain_logic.files;

import domain_logic.enums.Tag;
import domain_logic.producer.UploaderImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LicensedVideoFileTest {

    UploaderImpl up1 = new UploaderImpl("Hans");
    Collection<Tag> tags =  new ArrayList<>(Arrays.asList(Tag.Lifestyle, Tag.News));
    BigDecimal bitrate = new BigDecimal("48.000");
    Duration length = Duration.ofSeconds(215);
    String holder = "Universal";
    int resolution = 1080;

    //Date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar today = Calendar.getInstance();
    Date todayAsDate = today.getTime();

    LicensedVideoFile mediaElement = new LicensedVideoFile(up1,
            tags,
            bitrate,
            length,
            holder,
            resolution);

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
        assertEquals("LicensedVideo",mediaElement.typeString());
    }

    @Test
    void testToString() {
        assertEquals("\tLicensedVideo\tHans\t[Lifestyle, News]\t0\t48.000\tPT3M35S\t10320.000\t"+sdf.format(todayAsDate)+"\tUniversal\t1080",
                mediaElement.toString());
    }
}