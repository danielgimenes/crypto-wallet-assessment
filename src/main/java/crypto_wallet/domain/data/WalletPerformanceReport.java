package crypto_wallet.domain.data;

import java.math.BigDecimal;

public record WalletPerformanceReport(BigDecimal total, String bestAsset, Double bestPerformance, String worstAsset, Double worstPerformance) {}
