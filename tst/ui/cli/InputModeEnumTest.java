package ui.cli;

import org.junit.jupiter.api.Test;
import routing.InputModeEnum;

import static org.junit.jupiter.api.Assertions.*;

class InputModeEnumTest {

    @Test
    void testToString() {
        assertEquals("create:     ", InputModeEnum.c.toString()); }

}