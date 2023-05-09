package crypto_wallet.domain;

import crypto_wallet.domain.data.CryptoAsset;
import org.junit.jupiter.api.Test;
import test_utils.Formatters;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletPerformanceTests {

    @Test
    public void totalValueWhenSingleAsset() {
        List<CryptoAsset> assets = List.of(
                new CryptoAsset("BTC", 10, new BigDecimal("150.00"))
        );
        Map<String, BigDecimal> prices = Map.of(
                "BTC", new BigDecimal("40000.00")
        );

        BigDecimal expected = new BigDecimal("400000.00");
        assertEquals(expected, WalletPerformance.updatedTotal(assets, prices, Formatters.defaultMoneyFormatter));
    }

    @Test
    public void totalValueWhenMultipleAssets() {
        List<CryptoAsset> assets = List.of(
                new CryptoAsset("BTC", 10, new BigDecimal("150.00")),
                new CryptoAsset("ETH", 20, new BigDecimal("180.00"))
        );
        Map<String, BigDecimal> prices = Map.of(
                "BTC", new BigDecimal("30000.00"),
                "ETH", new BigDecimal("20000.00")
        );

        BigDecimal expected = new BigDecimal("700000.00");
        assertEquals(expected, WalletPerformance.updatedTotal(assets, prices, Formatters.defaultMoneyFormatter));
    }

    @Test
    public void totalValueWhenCurrentPriceUnavailable() {

    }

    @Test
    public void totalValueWhenCurrentPriceIsZero() {

    }
}
