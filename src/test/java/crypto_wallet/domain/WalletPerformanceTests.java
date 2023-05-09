package crypto_wallet.domain;

import crypto_wallet.domain.data.AssetPerformance;
import crypto_wallet.domain.data.CryptoAsset;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static test_utils.Formatters.defaultMoneyFormatter;

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
        assertEquals(expected, WalletPerformance.updatedTotal(assets, prices, defaultMoneyFormatter));
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
        assertEquals(expected, WalletPerformance.updatedTotal(assets, prices, defaultMoneyFormatter));
    }

    @Test
    public void totalValueWhenCurrentPriceIsZero() {
        List<CryptoAsset> assets = List.of(
                new CryptoAsset("BTC", 10, new BigDecimal("150.00")),
                new CryptoAsset("ETH", 20, new BigDecimal("180.00"))
        );
        Map<String, BigDecimal> prices = Map.of(
                "BTC", new BigDecimal("0.00"),
                "ETH", new BigDecimal("0.00")
        );

        BigDecimal expected = new BigDecimal("0.00");
        assertEquals(expected, WalletPerformance.updatedTotal(assets, prices, defaultMoneyFormatter));
    }

    @Test
    public void totalValueWhenCurrentPriceUnavailable() {
        List<CryptoAsset> assets = List.of(
                new CryptoAsset("BTC", 10, new BigDecimal("150.00"))
        );
        Map<String, BigDecimal> prices = Map.of();

        assertThrows(RuntimeException.class,
                () -> WalletPerformance.updatedTotal(assets, prices, defaultMoneyFormatter));
    }

    @Test
    public void calculatePerformancesSingleAssets() {
        List<CryptoAsset> assets = List.of(
                new CryptoAsset("BTC", 10, new BigDecimal("1000.00"))
        );
        Map<String, BigDecimal> prices = Map.of(
                "BTC", new BigDecimal("3000.00")
        );

        List<AssetPerformance> expected = List.of(
                new AssetPerformance("BTC", 3.0)
        );
        assertEquals(expected, WalletPerformance.calculatePerformances(assets, prices, defaultMoneyFormatter));
    }

    @Test
    public void calculatePerformancesMultipleAssets() {
        List<CryptoAsset> assets = List.of(
                new CryptoAsset("BTC", 10, new BigDecimal("1000.00")),
                new CryptoAsset("ETH", 20, new BigDecimal("3000.00"))
        );
        Map<String, BigDecimal> prices = Map.of(
                "BTC", new BigDecimal("1500.00"),
                "ETH", new BigDecimal("4000.00")
        );

        List<AssetPerformance> expected = List.of(
            new AssetPerformance("BTC", 1.5),
            new AssetPerformance("ETH", 1.33)
        );
        assertEquals(expected, WalletPerformance.calculatePerformances(assets, prices, defaultMoneyFormatter));
    }

    @Test
    public void calculatePerformancesZeroedPrice() {
        List<CryptoAsset> assets = List.of(
                new CryptoAsset("BTC", 10, new BigDecimal("1000.00")),
                new CryptoAsset("ETH", 20, new BigDecimal("3000.00"))
        );
        Map<String, BigDecimal> prices = Map.of(
                "BTC", new BigDecimal("0.00"),
                "ETH", new BigDecimal("0.00")
        );

        List<AssetPerformance> expected = List.of(
                new AssetPerformance("BTC", 0.0),
                new AssetPerformance("ETH", 0.0)
        );
        assertEquals(expected, WalletPerformance.calculatePerformances(assets, prices, defaultMoneyFormatter));
    }

    @Test
    public void calculatePerformancesPriceUnavailable() {
        List<CryptoAsset> assets = List.of(
                new CryptoAsset("BTC", 10, new BigDecimal("1000.00")),
                new CryptoAsset("ETH", 20, new BigDecimal("3000.00"))
        );
        Map<String, BigDecimal> prices = Map.of();

        assertThrows(RuntimeException.class,
                () -> WalletPerformance.calculatePerformances(assets, prices, defaultMoneyFormatter));
    }
}
