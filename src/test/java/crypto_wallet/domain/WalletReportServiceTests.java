package crypto_wallet.domain;

import crypto_wallet.domain.data.CryptoAsset;
import crypto_wallet.domain.data.WalletPerformanceReport;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test_utils.Formatters.*;

public class WalletReportServiceTests {

    @Test
    public void performanceReportWhenSingleAssets() {
        List<CryptoAsset> assets = List.of(
                new CryptoAsset("BTC", 0.12345, defaultNumberFormatter.parseMoney("37870.5058"))
        );

        AssetPriceAPI api = symbols -> Map.of(
                "BTC", defaultNumberFormatter.parseMoney("56999.9728252053067291")
        );
        WalletReportService service = new WalletReportService(api, defaultNumberFormatter);

        WalletPerformanceReport expected = new WalletPerformanceReport(
                new BigDecimal("7036.65"),
                "BTC",
                1.51,
                "BTC",
                1.51
        );
        assertEquals(expected, service.performanceReport(assets));
    }

    @Test
    public void performanceReportWhenMultipleAssets() {
        List<CryptoAsset> assets = List.of(
            new CryptoAsset("BTC", 0.12345, defaultNumberFormatter.parseMoney("37870.5058")),
            new CryptoAsset("ETH", 4.89532, defaultNumberFormatter.parseMoney("2004.9774"))
        );

        AssetPriceAPI api = symbols -> Map.of(
                "BTC", defaultNumberFormatter.parseMoney("56999.9728252053067291"),
                "ETH", defaultNumberFormatter.parseMoney("2032.1394325557042107")
        );
        WalletReportService service = new WalletReportService(api, defaultNumberFormatter);

        WalletPerformanceReport expected = new WalletPerformanceReport(
                new BigDecimal("16984.62"),
                "BTC",
                1.51,
                "ETH",
                1.01
        );
        assertEquals(expected, service.performanceReport(assets));
    }

}
