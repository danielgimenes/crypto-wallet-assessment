package crypto_wallet.domain;

import crypto_wallet.domain.data.AssetPerformance;
import crypto_wallet.domain.data.CryptoAsset;
import crypto_wallet.domain.data.WalletPerformance;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WalletPerformanceService {

    private final AssetPriceAPI assetPriceAPI;

    public WalletPerformanceService(AssetPriceAPI assetPriceAPI) {
        this.assetPriceAPI = assetPriceAPI;
    }

    public WalletPerformance report(List<CryptoAsset> assets) {
        Map<String, BigDecimal> currentPrices =
                assetPriceAPI.fetchPrices(assets.stream().map(CryptoAsset::symbol).toList());

        List<AssetPerformance> performances = calculatePerformances(assets, currentPrices);
        AssetPerformance best = performances.stream().max(Comparator.comparingDouble(AssetPerformance::percent)).orElseThrow();
        AssetPerformance worst = performances.stream().min(Comparator.comparingDouble(AssetPerformance::percent)).orElseThrow();

        return new WalletPerformance(
                updatedTotal(assets, currentPrices),
                best.symbol(),
                best.percent(),
                worst.symbol(),
                worst.percent()
        );
    }

    protected BigDecimal updatedTotal(List<CryptoAsset> assets, Map<String, BigDecimal> currentPrices) {
        return new BigDecimal(1);
    }

    protected List<AssetPerformance> calculatePerformances(List<CryptoAsset> assets, Map<String, BigDecimal> currentPrices) {
        return assets.stream().map(asset -> new AssetPerformance(asset.symbol(), 100.0)).toList();
    }
}
