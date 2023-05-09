package crypto_wallet.domain;

import crypto_wallet.domain.data.CryptoAsset;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletPerformanceTests {

    @Test
    public void totalValueWhenSingleAsset() {
        List<CryptoAsset> assets = List.of(
                new CryptoAsset("BTC", 10, new BigDecimal("150.00")),
                new CryptoAsset("ETH", 20, new BigDecimal("180.00"))
        );
        Map<String, BigDecimal> prices = Map.of(
                "BTC", new BigDecimal("300.00"),
                "ETH", new BigDecimal("200.00")
        );

        BigDecimal expected = new BigDecimal("7000.00");
        assertEquals(expected, WalletPerformance.updatedTotal(assets, prices));
    }

    @Test
    public void totalValueWhenMultipleAssets() {

    }

    @Test
    public void totalValueWhenCurrentPriceUnavailable() {

    }

    @Test
    public void totalValueWhenCurrentPriceIsZero() {

    }
}
