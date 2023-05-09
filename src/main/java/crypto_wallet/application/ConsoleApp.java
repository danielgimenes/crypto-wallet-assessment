package crypto_wallet.application;

import crypto_wallet.api.CoincapAPI;
import crypto_wallet.application.io.PerformanceReportWriter;
import crypto_wallet.application.io.WalletReader;
import crypto_wallet.domain.AssetPriceAPI;
import crypto_wallet.domain.WalletReportService;
import crypto_wallet.domain.data.CryptoAsset;
import crypto_wallet.domain.data.WalletPerformanceReport;
import currency.MoneyFormatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class ConsoleApp {

    private final WalletReader walletReader;

    private final WalletReportService walletReportService;

    private final PerformanceReportWriter performanceReportWriter;

    public static void main(String[] args) {
        if (!new File(args[0]).canRead()) {
            System.err.printf("Can't read file at '%s'. Be sure it is a valid wallet CSV file.", args[0]);
            return;
        }
        new ConsoleApp().walletPerformanceReport(args[0]);
    }

    public ConsoleApp() {
        MoneyFormatter moneyFormatter = new MoneyFormatter(Locale.US,
                '.',
                null,
                2,
                RoundingMode.HALF_UP
        );
        walletReader = new WalletReader(moneyFormatter);
        AssetPriceAPI api = new CoincapAPI(moneyFormatter);
        walletReportService = new WalletReportService(api, moneyFormatter);
        performanceReportWriter = new PerformanceReportWriter(moneyFormatter);
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
