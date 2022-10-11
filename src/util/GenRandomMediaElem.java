package util;

import java.util.Random;

public final class GenRandomMediaElem {
    private final String[] uploaderArray = {"Max","Moritz"};
    private final String[] mediaType = {"Audio","AudioVideo","InteractiveVideo","LicensedAudio","LicensedAudioVideo","LicensedVideo","Video"};
    private final Integer[] bitRateArray = {128,320,768};
    private final Integer[] durationArray ={60,120,180,360,720};
    private final Integer[] samplingRateArray ={44000,48000,96000};
    private final String[] exampleTag = {"News","Lifestyle","Animal","Tutorial"};
    Random rand = new Random();

    public String[] generateRandomMedia() {
        return new String[]{random(mediaType),random(uploaderArray), random(exampleTag), random(bitRateArray).toString(), random(durationArray).toString(), random(samplingRateArray).toString()};
    }

    //source: http://www.java2s.com/example/java-utility-method/random-element/randomelement-t-array-f117b.html
    private <T> T random(T[] array) {
        int randomIndex = this.rand.nextInt( array.length );
        return array[randomIndex];
    }
    /*
    //source:https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum

    private String randomSingleTag(){
         return Tag.values()[new Random().nextInt(Tag.values().length)];
     */
}
