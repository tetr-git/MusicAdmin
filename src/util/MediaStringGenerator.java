package util;

public final class MediaStringGenerator {
    public static String[] generateMediaString() {
        GenRandomMediaElem m = new GenRandomMediaElem();
        return m.generateRandomMedia();
    }
}
