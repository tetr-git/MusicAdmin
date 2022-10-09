package util;

import domain_logic.enums.Tag;

import java.util.Random;

public final class GenRandomMediaElem {
    private final String[] uploaderArray = {"Max","Moritz"};
    private final String[] mediaType = {"Audio","AudioVideo","InteractiveVideo","LicensedAudio","LicensedAudioVideo","LicensedVideo","Video"};
    private final Integer[] bitRateArray = {128,320,768};
    private final Integer[] durationArray ={60,120,180,360,720};
    private final Integer[] samplingRateArray ={44000,48000,96000};
    Random rand = new Random();

    public String[] generateRandomMedia() {
        return new String[]{random(mediaType),random(uploaderArray), randomSingleTag().toString(), random(bitRateArray).toString(), random(durationArray).toString(), random(samplingRateArray).toString()};
    }

    // http://www.java2s.com/example/java-utility-method/random-element/randomelement-t-array-f117b.html
    private <T> T random(T[] array) {
        int randomIndex = this.rand.nextInt( array.length );
        return array[randomIndex];
    }
    //todo überarbeiten nur single Tag
    //https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
    private Tag randomSingleTag(){
        return Tag.values()[new Random().nextInt(Tag.values().length)];
    }
}
/*
    private CustomerImpl customer;

    private Duration[] durations = {Duration.between(LocalTime.now(),LocalTime.now().plusHours(10)),Duration.between(LocalTime.now(),LocalTime.now().plusHours(20))};
    private BigDecimal[] valueOfItems = { new BigDecimal( "100" ), new BigDecimal( "500" ) };
    private Date[] dates = {new Date(),new Date(new Date().getTime()+24*69*60*1000)};
    //https://stackoverflow.com/questions/428918/how-can-i-increment-a-date-by-one-day-in-java/23910924#23910924
    Random rand = new Random();

    public CargoGenerator(CustomerImpl customer) {
        this.customer = customer;
    }

    public StorageItemImpl generateCargo (){
        //todo randomize Cargotyp
        return (new DryBulkCargoImpl(customer, random(durations), random(valueOfItems), Collections.singleton(randomSingleHazard()), random(dates)));

    }
    // http://www.java2s.com/example/java-utility-method/random-element/randomelement-t-array-f117b.html
    private <T> T random(T[] array) {
        int randomIndex = this.rand.nextInt( array.length );
        return array[randomIndex];
    }
    //todo überarbeiten nur single Hazard
    //https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
    private Hazard randomSingleHazard(){
        return Hazard.values()[new Random().nextInt(Hazard.values().length)];
    }

 */