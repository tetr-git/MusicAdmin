package util;

import org.junit.jupiter.api.Test;
import ui.cli.ParseCreate;

import static org.junit.jupiter.api.Assertions.*;

class ParseMediaTest {

    @Test
    void collectTags() {
        String test = "Lifestyle";
        ParseMedia parseMedia = new ParseMedia();

        assertEquals("", parseMedia.collectTags(test));


    }
}