package idjinn.finance.util;

public class Strings {
    public static String maskEmail(String email) {
        return email.replaceAll("(^[^@]{4}|(?!^)\\G)[^@]", "$1*");
    }
}
