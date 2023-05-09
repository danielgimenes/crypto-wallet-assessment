package crypto_wallet.domain;

import crypto_wallet.domain.data.AssetPerformance;
import crypto_wallet.domain.data.CryptoAsset;
import format.NumberFormatter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public final class WalletPerformance {

    private WalletPerformance() {}

    public static BigDecimal updatedTotal(List<CryptoAsset> assets, Map<String, BigDecimal> currentPrices, NumberFormatter formatter) {
        return assets.stream()
                .map(asset -> {
                    BigDecimal price = currentPrices.get(asset.symbol());
                    if (price == null) {
                        throw new RuntimeException(String.format("Price of %s unavailable", asset.symbol()));
                    }
                    return price.doubleValue() * asset.quantity();
                })
                .reduce(Double::sum)
                .map(total -> formatter.parseMoneyOrNull(String.valueOf(total)))
                .orElseThrow();
    }

    public static List<AssetPerformance> calculatePerformances(List<CryptoAsset> assets, Map<String, BigDecimal> currentPrices, NumberFormatter formatter) {
        return assets.stream()
                .map(asset -> {
                    BigDecimal price = currentPrices.get(asset.symbol());
                    if (price == null) {
                        throw new RuntimeException(String.format("Price of %s unavailable", asset.symbol()));
                    }
                    Double rate = price.divide(asset.price(), formatter.roundingMode).doubleValue(); // rounding already set in price BigDecimal
                    return new AssetPerformance(asset.symbol(), rate);
                })
                .toList();
    }

}
