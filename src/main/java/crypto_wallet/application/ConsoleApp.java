package crypto_wallet.application;

import crypto_wallet.api.CoincapAPI;
import crypto_wallet.application.io.PerformanceReportWriter;
import crypto_wallet.application.io.WalletReader;
import crypto_wallet.domain.AssetPriceAPI;
import crypto_wallet.domain.WalletReportService;
import crypto_wallet.domain.data.CryptoAsset;
import crypto_wallet.domain.data.WalletPerformanceReport;
import format.NumberFormatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;

public class ConsoleApp {

    private final WalletReader walletReader;

    private final WalletReportService walletReportService;

    private final PerformanceReportWriter performanceReportWriter;

    public static void main(String[] args) {
        String csvFilepath = Config.DEFAULT_WALLET_CSV_FILEPATH;
        if (args.length > 0) {
            csvFilepath = args[0];
        }
        if (!new File(csvFilepath).canRead()) {
            System.err.printf("Can't read file at '%s'. Be sure it is a path to a valid CSV file.", csvFilepath);
            return;
        }
        new ConsoleApp().walletPerformanceReport(csvFilepath);
    }

    public ConsoleApp() {
        NumberFormatter numberFormatter = new NumberFormatter(
                Config.LOCALE,
                Config.DECIMAL_SEPARATOR,
                Config.DOUBLE_FRACTIONAL_DIGITS,
                Config.MONEY_FRACTIONAL_DIGITS,
                RoundingMode.HALF_UP
        );
        walletReader = new WalletReader(numberFormatter);
        AssetPriceAPI api = new CoincapAPI(numberFormatter, Config.FETCH_PRICE_PARALLELISM);
        walletReportService = new WalletReportService(api, numberFormatter);
        performanceReportWriter = new PerformanceReportWriter(numberFormatter);
    }

    private void walletPerformanceReport(String csvFilepath) {
        try {
            List<CryptoAsset> wallet = walletReader.readAssetsFromCSV(csvFilepath);
            WalletPerformanceReport report = walletReportService.performanceReport(wallet);
            performanceReportWriter.printReport(report);
        } catch (FileNotFoundException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
