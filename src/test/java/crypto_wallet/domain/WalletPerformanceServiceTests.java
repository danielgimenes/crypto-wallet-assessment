package crypto_wallet.domain;

import crypto_wallet.api.CoincapAPI;
import crypto_wallet.domain.data.CryptoAsset;
import crypto_wallet.domain.data.WalletPerformance;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletPerformanceServiceTests {

    @Test
    public void reportWhenMultipleAssets() {
        List<CryptoAsset> assets = new ArrayList<>();
        assets.add(new CryptoAsset("BTC", 1, new BigDecimal("1000.00")));
        assets.add(new CryptoAsset("ETH", 2, new BigDecimal("1000.00")));

        WalletPerformanceService walletService = new WalletPerformanceService(new CoincapAPI());
        WalletPerformance expected = new WalletPerformance(
                new BigDecimal("20000.00"),
                "BTC",
                100.0,
                "ETH",
                50.0
        );
        assertEquals(expected, walletService.report(assets));
    }
}
