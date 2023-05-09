package format;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberFormatterTests {

    @Test
    public void parseMoneyWhenIntegerNumber() throws ParseException {
        String source = "20000.00";

        NumberFormatter formatter = new NumberFormatter(
                Locale.US,
                '.',
                2,
                5,
                RoundingMode.HALF_UP
        );

        BigDecimal expected = BigDecimal.valueOf(20000.00).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, formatter.parseMoney(source));
    }

    @Test
    public void parseMoneyWhenCustomSeparator() throws ParseException {
        String source = "20000@00";

        NumberFormatter formatter = new NumberFormatter(
                Locale.US,
                '@',
                2,
                5,
                RoundingMode.HALF_UP
        );

        BigDecimal expected = BigDecimal.valueOf(20000.00).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, formatter.parseMoney(source));
    }

    @Test
    public void parseMoneyAndRoundUpWhenFractionalNumber() throws ParseException {
        String source = "20000.06999999999";

        NumberFormatter formatter = new NumberFormatter(
                Locale.US,
                '.',
                2,
                5,
                RoundingMode.HALF_UP
        );

        BigDecimal expected = BigDecimal.valueOf(20000.07).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, formatter.parseMoney(source));
    }

    @Test
    public void formatMoneyWhenIntegerNumber() {
        BigDecimal source = BigDecimal.valueOf(20000).setScale(2, RoundingMode.HALF_UP);

        NumberFormatter formatter = new NumberFormatter(
                Locale.US,
                '.',
                2,
                5,
                RoundingMode.HALF_UP
        );

        assertEquals("20000.00", formatter.formatMoney(source));
    }

    @Test
    public void formatMoneyWhenGroupingDisabled() {
        BigDecimal source = BigDecimal.valueOf(20000).setScale(2, RoundingMode.HALF_UP);

        NumberFormatter formatter = new NumberFormatter(
                Locale.US,
                '.',
                2,
                5,
                RoundingMode.HALF_UP
        );

        assertEquals("20000.00", formatter.formatMoney(source));
    }

    @Test
    public void formatMoneyWhenFractionalNumber() {
        BigDecimal source = BigDecimal.valueOf(20000.12).setScale(2, RoundingMode.HALF_UP);

        NumberFormatter formatter = new NumberFormatter(
                Locale.US,
                '.',
                2,
                5,
                RoundingMode.HALF_UP
        );

        assertEquals("20000.12", formatter.formatMoney(source));
    }

    @Test
    public void formatMoneyAndRoundUpWhenFractionalNumber() {
        BigDecimal source = BigDecimal.valueOf(20000.12777).setScale(2, RoundingMode.HALF_UP);

        NumberFormatter formatter = new NumberFormatter(
                Locale.US,
                '.',
                2,
                5,
                RoundingMode.HALF_UP
        );

        assertEquals("20000.13", formatter.formatMoney(source));
    }

    @Test
    public void formatDoubleCustomSeparator() {
        Double source = 1.234;

        NumberFormatter formatter = new NumberFormatter(
                Locale.US,
                '@',
                2,
                5,
                RoundingMode.HALF_UP
        );

        assertEquals("1@234", formatter.formatDouble(source, 3));
    }

    @Test
    public void formatDoubleCustomFractionDigits() {
        Double source = 1.23456789;

        NumberFormatter formatter = new NumberFormatter(
                Locale.US,
                '@',
                2,
                5,
                RoundingMode.HALF_UP
        );

        assertEquals("1@23", formatter.formatDouble(source, 2));
    }

    @Test
    public void parseDoubleCustomSeparator() throws ParseException {
        String source = "1@234";

        NumberFormatter formatter = new NumberFormatter(
                Locale.US,
                '@',
                2,
                5,
                RoundingMode.HALF_UP
        );

        assertEquals(1.234, formatter.parseDouble(source));
    }

}
