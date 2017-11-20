package com.codecool.krk.lucidmotors.queststore.enums;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class EnumUtils {
    private static final String defaultValueNameReference = "DEFAULT";

    public static <E extends Enum<E>> E getValue(Class<E> givenClass, final String identity) {
        E[] enumConstants = givenClass.getEnumConstants();
        isClassValid(enumConstants);

        E expectedEnum = findEnum(enumConstants, identity);
        Supplier<E> defaultValue = () -> getDefaultValue(enumConstants);

        return expectedEnum != null ? expectedEnum : defaultValue.get();
    }

    private static <E extends Enum<E>> void isClassValid(E[] enumConstants) {
        if (getDefaultValue(enumConstants) == null) {
            throw new IllegalStateException("Delivered enum class must contain \"DEFAULT\" value");
        }
    }

    private static <E extends Enum<E>> E getDefaultValue(E[] enumConstants) {
        return findEnum(enumConstants, defaultValueNameReference);
    }

    private static <E extends Enum<E>> E findEnum(E[] enumConstants, final String givenName) {
        BiPredicate<E, String> isEqualByName = (enumConst, identity) -> enumConst.toString()
                                                                                 .equals(identity);

        return Arrays.stream(enumConstants)
                     .filter(e -> isEqualByName.test(e, givenName))
                     .findFirst()
                     .orElse(null);
    }
}
