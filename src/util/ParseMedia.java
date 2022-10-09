package util;

import domain_logic.enums.Tag;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;

public final class ParseMedia {

    //todo delete empty constructor
    //todo use ParseMedia in Event for Kapeselung
    public MediaAttributesCollection parseToCollection(String[] arg) {
        String errorMessage = "wrong tags";
        if (arg.length>4) {
            if (!collectTags(arg[2]).isEmpty()||(arg[2].equals(","))) {
                switch (arg[0].toLowerCase()) {
                    case "audio":
                        if (arg.length == 6) {
                            //full argumentList
                            return new MediaAttributesCollection(arg[0].toLowerCase(), arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg), addInteger(arg[5]));
                        }
                        if (arg.length == 5) {
                            return new MediaAttributesCollection(arg[0].toLowerCase(),arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg), 0);
                        }
                        errorMessage = "wrong number of attributes for audio file (5/6)";
                        break;
                    case "audiovideo":
                        if (arg.length == 7) {
                            //full argumentList
                            return new MediaAttributesCollection(arg[0].toLowerCase(),arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    addInteger(arg[5]), addInteger(arg[6]));
                        }
                        if (arg.length == 5) {
                            return new MediaAttributesCollection(arg[0].toLowerCase(),arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    0, 0);
                        }
                        errorMessage = "wrong number of attributes for audiovideo file (5/7)";
                        break;
                    case "interactivevideo":
                        if (arg.length == 7) {
                            //full argumentList
                            return new MediaAttributesCollection(arg[0].toLowerCase(), arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    (arg[5]), addInteger(arg[6]));
                        }
                        if (arg.length == 5) {
                            return new MediaAttributesCollection(arg[0].toLowerCase(),arg[1] , collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    "", 0);
                        }
                        errorMessage = "wrong number of attributes for interactivevideo file (5/7)";
                        break;
                    case "licensedaudio":
                        if (arg.length == 7) {
                            //full argumentList
                            return new MediaAttributesCollection(arg[0].toLowerCase(), arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    addInteger(arg[5]), arg[6]);
                        }
                        if (arg.length == 5) {
                            return new MediaAttributesCollection(arg[0].toLowerCase(),arg[1] , collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    0, "");
                        }
                        errorMessage = "wrong number of attributes for licensedaudio file (5/7)";
                        break;
                    case "licensedaudiovideo":
                        if (arg.length == 8) {
                            //full argumentList
                            return new MediaAttributesCollection(arg[0].toLowerCase(), arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    addInteger(arg[5]), arg[6], addInteger(arg[7]));
                        }
                        if (arg.length == 5) {
                            return new MediaAttributesCollection(arg[0].toLowerCase(),arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    0, "", 0);
                        }
                        errorMessage = "wrong number of attributes for licensedaudiovideo file (5/8)";
                        break;
                    case "licensedvideo":
                        if (arg.length == 7) {
                            //full argumentList
                            return new MediaAttributesCollection(arg[0].toLowerCase(), arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    arg[5], addInteger(arg[6]));
                        }
                        if (arg.length == 5) {
                            return new MediaAttributesCollection(arg[0].toLowerCase(),arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    "", 0);
                        }
                        errorMessage = "wrong number of attributes for licensedvideo file (5/7)";
                        break;
                    case "video":
                        if (arg.length == 6) {
                            //full argumentList
                            return new MediaAttributesCollection(arg[0].toLowerCase(), arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    addInteger(arg[5]));
                        }
                        if (arg.length == 5) {
                            return new MediaAttributesCollection(arg[0].toLowerCase(),arg[1], collectTags(arg[2]),
                                    addBigDecimalBitrate(arg[3]), addDuration(arg),
                                    0);
                        }
                        errorMessage = "wrong number of attributes for video file (5/6)";
                        break;
                    default:
                        errorMessage = "Wrong File Type";
                }
            }
        }
        return new MediaAttributesCollection("", errorMessage);
    }

    public ArrayList<Tag> collectTags(String str) {
        ArrayList<Tag> tagArrayList = new ArrayList<>();
        //new ArrayList<Tag>(Arrays.asList(Tag.Lifestyle,Tag.News))
        if (!str.equals(",")) {
            String[] tags = str.split(",");
            //todo check for wrong input
            for (String tag : tags) {
                //Arrays.asList(Tag.values()).forEach(tag -> );
                for (Tag t : Tag.values()) {
                    if (t.toString().equalsIgnoreCase(tag)) {
                        tagArrayList.add(t);
                    }
                }
            }
        }
        return tagArrayList;
    }

    private BigDecimal addBigDecimalBitrate(String str) {
        BigDecimal bigDecimal = new BigDecimal(0);
        if (isNumeric(str)) {
            bigDecimal = BigDecimal.valueOf(Double.parseDouble(str));
        }
        return bigDecimal;
    }

    private Duration addDuration(String[] arg) {
        Duration duration = Duration.ofSeconds(0);
        if (isNumeric(arg[3])) {
            duration = Duration.ofSeconds((long) Double.parseDouble(arg[3]));
        }
        return duration;
    }

    private int addInteger(String str) {
        int i = 0;
        //alternativ  str.matches("-?\\d+");
        if (isNumericInteger(str)) {
            i = Integer.parseInt(str);
        }
        return i;
    }
    //todo delete -> util
    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static boolean isNumericInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;

    }
}
