package com.codecool.krk.lucidmotors.queststore.matchers;

import java.math.BigInteger;

public class Compare {
    public static Boolean isLower(BigInteger n1, BigInteger n2) {
        return n1.compareTo(n2) < 0;
    }

    public static Boolean isHigher(BigInteger n1, BigInteger n2) {
        return n1.compareTo(n2) > 0;
    }

    public static Boolean isEqual(BigInteger n1, BigInteger n2) {
        return n1.compareTo(n2) == 0;
    }

    public static Boolean isLowerOrEqual(BigInteger n1, BigInteger n2) {
        return n1.compareTo(n2) <= 0;
    }

    public static Boolean isHigherOrEqual(BigInteger n1, BigInteger n2) {
        return n1.compareTo(n2) >= 0;
    }
}
