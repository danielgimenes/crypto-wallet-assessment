package crypto_wallet.domain;

import crypto_wallet.domain.data.CryptoAsset;
import crypto_wallet.domain.data.WalletPerformanceReport;
import org.junit.jupiter.api.Test;
import test_utils.Formatters;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletReportServiceTests {

    @Test
    public void performanceReportWhenMultipleAssets() {
        List<CryptoAsset> assets = List.of(
            new CryptoAsset("BTC", 1, new BigDecimal("1000.00")),
            new CryptoAsset("ETH", 2, new BigDecimal("1000.00"))
        );

        AssetPriceAPI api = symbols -> Map.of(
                "BTC", new BigDecimal("1000.00"),
                "ETH", new BigDecimal("2000.00")
        );;
        WalletReportService service = new WalletReportService(api, Formatters.defaultMoneyFormatter);

        WalletPerformanceReport expected = new WalletPerformanceReport(
                new BigDecimal("4000.00"),
                "BTC",
                100.0,
                "ETH",
                50.0
        );
        assertEquals(expected, service.performanceReport(assets));
    }
}
