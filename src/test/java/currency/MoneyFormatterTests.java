package currency;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyFormatterTests {

    @Test
    public void parseWhenIntegerNumber() throws ParseException {
        String source = "20000.00";

        MoneyFormatter formatter = new MoneyFormatter(
                Locale.US,
                '.',
                ',',
                2,
                RoundingMode.HALF_UP
        );

        BigDecimal expected = BigDecimal.valueOf(20000.00).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, formatter.parse(source));
    }

    @Test
    public void parseWhenCustomSeparator() throws ParseException {
        String source = "20[000@00";

        MoneyFormatter formatter = new MoneyFormatter(
                Locale.US,
                '@',
                '[',
                2,
                RoundingMode.HALF_UP
        );

        BigDecimal expected = BigDecimal.valueOf(20000.00).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, formatter.parse(source));
    }

    @Test
    public void parseAndRoundUpWhenFractionalNumber() throws ParseException {
        String source = "20000.06999999999";

        MoneyFormatter formatter = new MoneyFormatter(
                Locale.US,
                '.',
                ',',
                2,
                RoundingMode.HALF_UP
        );

        BigDecimal expected = BigDecimal.valueOf(20000.07).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, formatter.parse(source));
    }

    @Test
    public void formatWhenIntegerNumber() {
        BigDecimal source = BigDecimal.valueOf(20000).setScale(2, RoundingMode.HALF_UP);

        MoneyFormatter formatter = new MoneyFormatter(
                Locale.US,
                '.',
                ',',
                2,
                RoundingMode.HALF_UP
        );

        assertEquals("20,000.00", formatter.format(source));
    }

    @Test
    public void formatWhenGroupingDisabled() {
        BigDecimal source = BigDecimal.valueOf(20000).setScale(2, RoundingMode.HALF_UP);

        MoneyFormatter formatter = new MoneyFormatter(
                Locale.US,
                '.',
                null,
                2,
                RoundingMode.HALF_UP
        );

        assertEquals("20000.00", formatter.format(source));
    }

    @Test
    public void formatWhenFractionalNumber() {
        BigDecimal source = BigDecimal.valueOf(20000.12).setScale(2, RoundingMode.HALF_UP);

        MoneyFormatter formatter = new MoneyFormatter(
                Locale.US,
                '.',
                null,
                2,
                RoundingMode.HALF_UP
        );

        assertEquals("20000.12", formatter.format(source));
    }

    @Test
    public void formatAndRoundUpWhenFractionalNumber() {
        BigDecimal source = BigDecimal.valueOf(20000.12777).setScale(2, RoundingMode.HALF_UP);

        MoneyFormatter formatter = new MoneyFormatter(
                Locale.US,
                '.',
                null,
                2,
                RoundingMode.HALF_UP
        );

        assertEquals("20000.13", formatter.format(source));
    }

}