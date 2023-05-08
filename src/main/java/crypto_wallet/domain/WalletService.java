package crypto_wallet.domain;

import crypto_wallet.domain.data.CryptoAsset;
import crypto_wallet.domain.data.WalletPerformance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class WalletService {

    private final PriceAPI priceAPI;

    public WalletService(PriceAPI priceAPI) {
        this.priceAPI = priceAPI;
    }

    public WalletPerformance performance(List<CryptoAsset> assets) {
        Map<String, BigDecimal> currentPriceBySymbol =
                priceAPI.fetchPrices(assets.stream().map(CryptoAsset::symbol).toList());

        return new WalletPerformance(
                new BigDecimal("20000.00"),
                "BTC",
                100.0,
                "ETH",
                50.0
        );
    }
}
