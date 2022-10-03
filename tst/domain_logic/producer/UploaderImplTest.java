package domain_logic.producer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UploaderImplTest {

    String name = "hans";
    UploaderImpl uploader = new UploaderImpl(name);

    @Test
    void getName(){
        assertEquals(name,uploader.getName());
    }

    @Test
    void testToString() {
        assertEquals("Uploader:hans ",uploader.toString());
    }
}