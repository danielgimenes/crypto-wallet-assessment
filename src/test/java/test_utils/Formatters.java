package test_utils;

import currency.MoneyFormatter;

import java.math.RoundingMode;
import java.util.Locale;

public class Formatters {

    private Formatters() {}

    public static final MoneyFormatter defaultMoneyFormatter = new MoneyFormatter(Locale.US,
            '.',
            null,
            2,
            RoundingMode.HALF_UP
    );
}
