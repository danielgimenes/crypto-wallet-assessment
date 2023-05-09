package currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class NumberFormatter {

    private final DecimalFormat formatter;

    private final int fractionDigits;

    public final RoundingMode roundingMode;

    public NumberFormatter(Locale locale, char decimalSeparator, Character groupingSeparator, int fractionDigits, RoundingMode roundingMode) {
        this.fractionDigits = fractionDigits;
        this.roundingMode = roundingMode;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setDecimalSeparator(decimalSeparator);
        formatter = new DecimalFormat();
        if (groupingSeparator != null) {
            symbols.setGroupingSeparator(groupingSeparator);
        } else {
            formatter.setGroupingUsed(false);
        }
        formatter.setDecimalFormatSymbols(symbols);
        formatter.setRoundingMode(roundingMode);
        formatter.setMaximumFractionDigits(fractionDigits);
        formatter.setMinimumFractionDigits(fractionDigits);
    }

    public BigDecimal parseMoneyOrNull(String value) {
        try {
            return parseMoney(value);
        } catch (ParseException e) {
            return null;
        }
    }

    public BigDecimal parseMoney(String value) throws ParseException {
        return BigDecimal.valueOf(formatter.parse(value).doubleValue())
                .setScale(fractionDigits, formatter.getRoundingMode());
    }

    public String formatMoney(BigDecimal value) {
        return formatter.format(value);
    }

    public Double parseDouble(String value) {

    }

    public String formatDouble(Double double) {
    }

}
