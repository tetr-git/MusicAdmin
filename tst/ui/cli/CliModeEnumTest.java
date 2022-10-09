package ui.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CliModeEnumTest {

    @Test
    void testToString() {
        assertEquals("create:     ",CliModeEnum.c.toString()); }

}