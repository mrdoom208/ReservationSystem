package com.Springboot.Connection.util;

public class PhoneFormatter {
    public static String normalizePHNumber(String number) {
        if (number == null) {
            throw new IllegalArgumentException("Phone number is null");
        }

        // Remove spaces, dashes, parentheses
        number = number.trim().replaceAll("[^0-9+]", "");

        // +639XXXXXXXXX → 639XXXXXXXXX
        if (number.startsWith("+639")) {
            return number.substring(1);
        }

        // 09XXXXXXXXX → 639XXXXXXXXX
        if (number.startsWith("09")) {
            return "639" + number.substring(2);
        }

        // Already correct
        if (number.startsWith("639") && number.length() == 12) {
            return number;
        }

        throw new IllegalArgumentException("Invalid PH mobile number: " + number);
    }

}
