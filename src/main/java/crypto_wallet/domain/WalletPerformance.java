package crypto_wallet.domain;

import crypto_wallet.domain.data.AssetPerformance;
import crypto_wallet.domain.data.CryptoAsset;
import currency.MoneyFormatter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public final class WalletPerformance {

    private WalletPerformance() {}

    public static BigDecimal updatedTotal(List<CryptoAsset> assets, Map<String, BigDecimal> currentPrices, MoneyFormatter formatter) {
        return assets.stream()
                .map(asset -> currentPrices.get(asset.symbol()).doubleValue() * asset.quantity())
                .reduce(Double::sum)
                .map(total -> formatter.parseOrNull(total.toString()))
                .orElseThrow();
    }

    public static List<AssetPerformance> calculatePerformances(List<CryptoAsset> assets, Map<String, BigDecimal> currentPrices) {
        return assets.stream().map(asset -> new AssetPerformance(asset.symbol(), 100.0)).toList();
    }

}
