package util;

public final class TypeConverter {

    public static boolean isNumericInteger(String strNum) {
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

    public static int addInteger(String str) {
        int i = 0;
        //alternativ  str.matches("-?\\d+");
        if (isNumericInteger(str)) {
            i = Integer.parseInt(str);
        }
        return i;
    }
}
