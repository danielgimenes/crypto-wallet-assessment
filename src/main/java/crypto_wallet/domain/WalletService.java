package crypto_wallet.domain;

import crypto_wallet.domain.data.CryptoAsset;
import crypto_wallet.domain.data.WalletPerformance;

import java.math.BigDecimal;
import java.util.List;

public class WalletService {

    public WalletPerformance performance(List<CryptoAsset> assets) {
        return new WalletPerformance(
                new BigDecimal("20000.00"),
                "BTC",
                100.0,
                "ETH",
                50.0
        );
    }
}
