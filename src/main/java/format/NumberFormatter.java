package format;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class NumberFormatter {

    private final DecimalFormat moneyFormatter;

    private final DecimalFormat doubleFormatter;

    private final int moneyFractionDigits;

    public final RoundingMode roundingMode;

    public NumberFormatter(Locale locale, char decimalSeparator, int moneyFractionDigits, int doubleFractionDigits,
                           RoundingMode roundingMode) {
        this.moneyFractionDigits = moneyFractionDigits;
        this.roundingMode = roundingMode;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setDecimalSeparator(decimalSeparator);
        moneyFormatter = new DecimalFormat();
        moneyFormatter.setGroupingUsed(false);
        moneyFormatter.setDecimalFormatSymbols(symbols);
        moneyFormatter.setRoundingMode(roundingMode);
        moneyFormatter.setMaximumFractionDigits(moneyFractionDigits);
        moneyFormatter.setMinimumFractionDigits(moneyFractionDigits);
        doubleFormatter = new DecimalFormat();
        doubleFormatter.setGroupingUsed(false);
        doubleFormatter.setDecimalFormatSymbols(symbols);
        doubleFormatter.setRoundingMode(roundingMode);
        doubleFormatter.setMaximumFractionDigits(doubleFractionDigits);
        doubleFormatter.setMinimumFractionDigits(doubleFractionDigits);
    }

    private NumberFormatter(DecimalFormat moneyFormatter, DecimalFormat doubleFormatter,
                            int moneyFractionDigits, RoundingMode roundingMode) {
        this.moneyFormatter = moneyFormatter;
        this.doubleFormatter = doubleFormatter;
        this.moneyFractionDigits = moneyFractionDigits;
        this.roundingMode = roundingMode;
    }

    public BigDecimal parseMoney(String value) {
        try {
            return BigDecimal.valueOf(moneyFormatter.parse(value).doubleValue())
                    .setScale(moneyFractionDigits, moneyFormatter.getRoundingMode());
        } catch (ParseException e) {
            return null;
        }
    }

    public String formatMoney(BigDecimal value) {
        return moneyFormatter.format(value);
    }

    public Double parseDouble(String value) throws ParseException {
        return doubleFormatter.parse(value).doubleValue();
    }

    public String formatDouble(Double value, int fractionDigits) {
        DecimalFormat formatter = (DecimalFormat) doubleFormatter.clone();
        formatter.setMaximumFractionDigits(fractionDigits);
        formatter.setMinimumFractionDigits(fractionDigits);
        return formatter.format(value);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public NumberFormatter clone() {
        return new NumberFormatter(
                (DecimalFormat) moneyFormatter.clone(), // not thread-safe
                (DecimalFormat) doubleFormatter.clone(), // not thread-safe
                moneyFractionDigits,
                roundingMode
        );
    }

}
