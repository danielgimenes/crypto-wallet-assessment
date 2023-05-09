package test_utils;

import format.NumberFormatter;

import java.math.RoundingMode;
import java.util.Locale;

public class Formatters {

    private Formatters() {}

    public static final NumberFormatter defaultNumberFormatter = new NumberFormatter(
            Locale.US,
            '.',
            2,
            5,
            RoundingMode.HALF_UP
    );
}
