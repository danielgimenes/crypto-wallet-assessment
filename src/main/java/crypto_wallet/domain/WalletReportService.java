package crypto_wallet.domain;

import crypto_wallet.domain.data.AssetPerformance;
import crypto_wallet.domain.data.CryptoAsset;
import crypto_wallet.domain.data.WalletPerformanceReport;
import format.NumberFormatter;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WalletReportService {

    private final AssetPriceAPI assetPriceAPI;

    private final NumberFormatter numberFormatter;

    public WalletReportService(AssetPriceAPI assetPriceAPI, NumberFormatter numberFormatter) {
        this.assetPriceAPI = assetPriceAPI;
        this.numberFormatter = numberFormatter;
    }

    public WalletPerformanceReport performanceReport(List<CryptoAsset> assets) {
        Map<String, BigDecimal> currentPrices =
                assetPriceAPI.fetchPrices(assets.stream().map(CryptoAsset::symbol).toList());

        List<AssetPerformance> performances = WalletPerformance.calculatePerformances(assets, currentPrices, numberFormatter);
        AssetPerformance best = performances.stream().max(Comparator.comparingDouble(AssetPerformance::rate)).orElseThrow();
        AssetPerformance worst = performances.stream().min(Comparator.comparingDouble(AssetPerformance::rate)).orElseThrow();

        return new WalletPerformanceReport(
                WalletPerformance.updatedTotal(assets, currentPrices, numberFormatter),
                best.symbol(),
                best.rate(),
                worst.symbol(),
                worst.rate()
        );
    }
}
