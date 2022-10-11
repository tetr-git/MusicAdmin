package util;

import java.util.Random;

public final class GenRandomMediaElem {
    private final String[] uploaderArray = {"MrOizo", "DjMehdi", "SebastiAn"};
    private final String[] mediaType = {"Audio", "AudioVideo", "InteractiveVideo", "LicensedAudio", "LicensedAudioVideo", "LicensedVideo", "Video"};
    private final Integer[] bitRateArray = {128, 320, 768};
    private final Integer[] durationArray = {60, 120, 180, 360, 720};
    Random rand = new Random();

    public String[] generateRandomMedia() {
        return new String[]{random(mediaType), random(uploaderArray), ",", random(bitRateArray).toString(), random(durationArray).toString()};
    }

    /**
     *
     * @source http://www.java2s.com/example/java-utility-method/random-element/randomelement-t-array-f117b.html
     */
    private <T> T random(T[] array) {
        int randomIndex = this.rand.nextInt(array.length);
        return array[randomIndex];
    }
}
