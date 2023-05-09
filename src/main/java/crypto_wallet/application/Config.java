package crypto_wallet.application;

import java.util.Locale;

public final class Config {

    private Config() {}

    public static final String DEFAULT_WALLET_CSV_FILEPATH = "src/main/resources/sample_wallet.csv";

    public static Locale LOCALE = Locale.US;

    public static int FETCH_PRICE_PARALLELISM = 3;

    public static char DECIMAL_SEPARATOR = '.';

    public static int DOUBLE_FRACTIONAL_DIGITS = 2;

    public static int MONEY_FRACTIONAL_DIGITS = 5;

}
