package currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class MoneyFormatter {

    private final DecimalFormat formatter;

    private final int fractionDigits;

    public MoneyFormatter(Locale locale, char decimalSeparator, Character groupingSeparator, int fractionDigits, RoundingMode roundingMode) {
        this.fractionDigits = fractionDigits;
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

    public BigDecimal parse(String value) throws ParseException {
        return BigDecimal.valueOf(formatter.parse(value).doubleValue())
                .setScale(fractionDigits, formatter.getRoundingMode());
    }

    public String format(BigDecimal value) {
        return formatter.format(value);
    }

}
