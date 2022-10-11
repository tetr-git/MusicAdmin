package util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RepoCollectionTest {

    @Test
    void getTwoAttributesToArray() {
        String[] arg = {"Hello","Test"};
        assertEquals(Arrays.toString(arg), Arrays.toString(new RepoCollection(arg).getAttributes()));
    }

    @Test
    void getThreeAttributesToArray() {
        String[] arg = {"Hello","Test","1"};
        assertEquals(Arrays.toString(arg), Arrays.toString(new RepoCollection(arg).getAttributes()));
    }
}