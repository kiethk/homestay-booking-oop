package utils;

public class Validator {

    public static final String HOME_ID = "^HS\\d{4}$";
    public static final String BOOKING_ID = "^[B]\\d{5}$";
    public static final String TOUR_ID = "^[T]\\d{5}$";
    public static final String NAME_VALID = "^.{2,25}$";
    public static final String PHONE_VALID = "^0\\d{9}$";
    public static final String INTEGER_VALID = "^-?\\d+$";
    public static final String POSITIVE_INT_VALID = "^[1-9]\\d*";
    public static final String DOUBLE_VALID = "^[+-]?(([1-9]\\d*)|0)(\\.\\d+)?$";
    public static final String POSITIVE_DOUBLE_VALID = "^[+]?(([1-9]\\d*)|0)(\\.\\d+)?$";
    public static final String BOOLEAN_VALID = "^[YyNn]$";
    public static final String EMAIL_VALID = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String DATE_VALID = "dd/MM/yyyy";

    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
}
