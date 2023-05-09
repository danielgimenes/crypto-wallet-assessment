package crypto_wallet.domain;

import crypto_wallet.domain.data.AssetPerformance;
import crypto_wallet.domain.data.CryptoAsset;
import crypto_wallet.domain.data.WalletPerformanceReport;
import currency.MoneyFormatter;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WalletReportService {

    private final AssetPriceAPI assetPriceAPI;

    private final MoneyFormatter moneyFormatter;

    public WalletReportService(AssetPriceAPI assetPriceAPI, MoneyFormatter moneyFormatter) {
        this.assetPriceAPI = assetPriceAPI;
        this.moneyFormatter = moneyFormatter;
    }

    public WalletPerformanceReport performanceReport(List<CryptoAsset> assets) {
        Map<String, BigDecimal> currentPrices =
                assetPriceAPI.fetchPrices(assets.stream().map(CryptoAsset::symbol).toList());

        List<AssetPerformance> performances = WalletPerformance.calculatePerformances(assets, currentPrices);
        AssetPerformance best = performances.stream().max(Comparator.comparingDouble(AssetPerformance::percent)).orElseThrow();
        AssetPerformance worst = performances.stream().min(Comparator.comparingDouble(AssetPerformance::percent)).orElseThrow();

        return new WalletPerformanceReport(
                WalletPerformance.updatedTotal(assets, currentPrices, moneyFormatter),
                best.symbol(),
                best.percent(),
                worst.symbol(),
                worst.percent()
        );
    }
}
