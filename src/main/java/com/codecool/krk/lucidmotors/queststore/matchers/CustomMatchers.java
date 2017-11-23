package com.codecool.krk.lucidmotors.queststore.Matchers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomMatchers {

    public static boolean isInteger(String value) {
        Matcher matcher = Pattern.compile("^-?[1-9]\\d*$")
                                 .matcher(value);

        return matcher.matches();
    }

    public static boolean isPositiveInteger(String value) {
        Matcher matcher = Pattern.compile("^[1-9]\\d*$")
                                 .matcher(value);

        return matcher.matches();
    }
}
